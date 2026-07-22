package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class SleepCalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sleep_calculator)

        val btnCalculate = findViewById<Button>(R.id.btnCalculateSleep)
        val resultText = findViewById<TextView>(R.id.sleepResultsText)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnCalculate.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 14) // Average time to fall asleep

            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val sb = StringBuilder()
            
            sb.append("You should wake up at:\n\n")
            
            // Calculate 6 sleep cycles (90 mins each)
            for (i in 1..4) {
                calendar.add(Calendar.MINUTE, 90)
                sb.append("Cycle $i: ${sdf.format(calendar.time)}\n")
            }
            
            resultText.text = sb.toString()
        }

        btnBack.setOnClickListener { finish() }
    }
}