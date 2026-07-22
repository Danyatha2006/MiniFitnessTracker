package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class SleepTrackerActivity : AppCompatActivity() {
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sleep_tracker)

        val statusText = findViewById<TextView>(R.id.sleepStatusText)
        val btnStart = findViewById<Button>(R.id.btnStartSleep)
        val btnEnd = findViewById<Button>(R.id.btnEndSleep)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnStart.setOnClickListener {
            startTime = System.currentTimeMillis()
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            statusText.text = "Sleeping since: ${sdf.format(Date(startTime))}"
        }

        btnEnd.setOnClickListener {
            if (startTime == 0L) {
                statusText.text = "You haven't started tracking yet!"
                return@setOnClickListener
            }
            val endTime = System.currentTimeMillis()
            val durationMillis = endTime - startTime
            val hours = durationMillis / (1000 * 60 * 60)
            val minutes = (durationMillis / (1000 * 60)) % 60
            
            statusText.text = "You slept for: $hours hours $minutes minutes"
            startTime = 0 // Reset
        }

        btnBack.setOnClickListener { finish() }
    }
}