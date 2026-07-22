package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CaloriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_calories)

        val result = findViewById<TextView>(R.id.result)
        val backBtn = findViewById<Button>(R.id.backBtn)

        val steps = intent.getIntExtra("steps", 0)

        val calories = steps * 0.04
        val distance = steps * 0.0008
        val goalPercent = (steps / 10000.0) * 100

        val graph = when {
            goalPercent >= 100 -> "██████████"
            goalPercent >= 80 -> "████████░░"
            goalPercent >= 60 -> "██████░░░░"
            goalPercent >= 40 -> "████░░░░░░"
            goalPercent >= 20 -> "██░░░░░░░░"
            else -> "█░░░░░░░░░"
        }

        val status = when {
            steps >= 10000 -> "Excellent"
            steps >= 7000 -> "Good"
            steps >= 4000 -> "Average"
            else -> "Needs More Activity"
        }

        result.text = """

Number of steps completed today: $steps

Calories Burned: ${"%.2f".format(calories)} kcal

Distance Covered: ${"%.2f".format(distance)} km

Goal Progress:
$graph ${goalPercent.toInt()}%

Fitness Status: $status
        """.trimIndent()

        // BACK TO DASHBOARD
        backBtn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}