package com.example.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesstracker.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var pref: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        pref = PrefManager(this)

        displayPreviousDayHistory()

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun displayPreviousDayHistory() {
        binding.historyContainer.removeAllViews()

        val workout = pref.getPreviousDayWorkout()

        if (workout == null) {
            val emptyText = TextView(this).apply {
                text = "No history available for yesterday"
                setTextColor(Color.WHITE)
                textSize = 16f
            }
            binding.historyContainer.addView(emptyText)
            return
        }

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundResource(R.drawable.glass_card_green)
            setPadding(24, 24, 24, 24)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 0, 0, 20)
            layoutParams = lp
        }

        val titleText = TextView(this).apply {
            text = "Workout on ${workout.date}"
            setTextColor(Color.parseColor("#39FF14"))
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        val stepsText = TextView(this).apply {
            text = "Steps: ${workout.steps}"
            setTextColor(Color.WHITE)
            textSize = 15f
        }

        val caloriesText = TextView(this).apply {
            text = "Calories: ${workout.calories} kcal"
            setTextColor(Color.WHITE)
            textSize = 15f
        }

        card.addView(titleText)
        card.addView(stepsText)
        card.addView(caloriesText)

        binding.historyContainer.addView(card)
    }
}