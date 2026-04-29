package com.example.fitnesstracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.*
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class TrackerActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private var initialSteps = -1f   // 🔥 FIX: better initialization
    private var isTracking = true

    private lateinit var stepsText: TextView
    private lateinit var pauseBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var backBtn: Button

    private val ACTIVITY_RECOGNITION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()   // 🔥 UI consistency

        setContentView(R.layout.activity_tracker)

        // Bind UI
        stepsText = findViewById(R.id.stepsText)
        pauseBtn = findViewById(R.id.pauseBtn)
        nextBtn = findViewById(R.id.nextBtn)
        backBtn = findViewById(R.id.backBtn)

        // Permission (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    ACTIVITY_RECOGNITION_CODE
                )
            }
        }

        // Sensor setup
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "Step Sensor NOT Available ❌", Toast.LENGTH_LONG).show()
        }

        // Pause / Resume
        pauseBtn.setOnClickListener {
            isTracking = !isTracking
            pauseBtn.text = if (isTracking) "Pause ⏸️" else "Resume ▶️"
        }

        // Next
        nextBtn.setOnClickListener {
            startActivity(Intent(this, CaloriesActivity::class.java))
        }

        // Back
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || !isTracking) return

        val totalSteps = event.values[0]

        // 🔥 FIX: set initial only once
        if (initialSteps == -1f) {
            initialSteps = totalSteps
        }

        val currentSteps = (totalSteps - initialSteps).toInt()

        // 🔥 FIX: no toast spam (this was freezing your app)
        stepsText.text = "$currentSteps Steps"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()

        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // 🔥 OPTIONAL: permission result handling (good practice)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ACTIVITY_RECOGNITION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied ❌", Toast.LENGTH_LONG).show()
            }
        }
    }
}