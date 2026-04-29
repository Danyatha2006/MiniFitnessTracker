package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_history)

        val historyData = findViewById<TextView>(R.id.historyData)
        val backBtn = findViewById<Button>(R.id.backBtn)

        try {
            val pref = PrefManager(this)

            val steps = pref.getSteps()
            val calories = pref.getCalories()

            if (steps == 0 && calories == 0) {
                historyData.text = "No history available yet"
            } else {
                historyData.text = "Steps: $steps\nCalories: $calories kcal"
            }

        } catch (e: Exception) {
            historyData.text = "Error loading history"
        }

        backBtn.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}