package com.example.fitnesstracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class TrackerActivity : AppCompatActivity(),
    SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private lateinit var stepsText: TextView
    private lateinit var pauseBtn: Button
    private lateinit var caloriesBtn: Button
    private lateinit var backBtn: Button

    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private var currentSteps = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_tracker)

        // Views
        stepsText = findViewById(R.id.stepsText)
        pauseBtn = findViewById(R.id.pauseBtn)
        caloriesBtn = findViewById(R.id.caloriesBtn)
        backBtn = findViewById(R.id.backBtn)

        // Permission for Android 10+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACTIVITY_RECOGNITION
                    ),
                    100
                )
            }
        }

        // Sensor setup
        sensorManager =
            getSystemService(SENSOR_SERVICE)
                    as SensorManager

        stepSensor =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_STEP_COUNTER
            )

        // Check sensor
        if (stepSensor == null) {

            stepsText.text =
                "Step Sensor Not Available"

            Toast.makeText(
                this,
                "Step Sensor Not Available",
                Toast.LENGTH_LONG
            ).show()

        } else {

            Toast.makeText(
                this,
                "Step Counter Ready ✅",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Pause / Resume
        pauseBtn.setOnClickListener {

            isPaused = !isPaused

            pauseBtn.text =
                if (isPaused)
                    "Resume Tracking"
                else
                    "Pause Tracking"
        }

        // Calories page
        caloriesBtn.setOnClickListener {

            val intent =
                Intent(
                    this,
                    CaloriesActivity::class.java
                )

            intent.putExtra(
                "steps",
                currentSteps
            )

            startActivity(intent)
        }

        // Back button
        backBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )

            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        stepSensor?.also {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(
        event: SensorEvent?
    ) {

        if (event != null && !isPaused) {

            totalSteps = event.values[0]

            // Save starting value once
            if (previousTotalSteps == 0f) {

                previousTotalSteps = totalSteps
            }

            // Calculate current steps
            currentSteps =
                (totalSteps - previousTotalSteps).toInt()

            // Prevent negative values
            if (currentSteps < 0) {
                currentSteps = 0
            }

            stepsText.text =
                "Steps: $currentSteps"
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {

    }
}