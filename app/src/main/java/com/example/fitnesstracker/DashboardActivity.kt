package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_dashboard)

        // Views
        val welcomeText = findViewById<TextView>(R.id.welcome)
        val chatbot = findViewById<Button>(R.id.btnChatbot)
        val tracker = findViewById<Button>(R.id.btnTracker)
        val water = findViewById<Button>(R.id.btnWater)
        val bmi = findViewById<Button>(R.id.btnBMI)
        val sleepCalc = findViewById<Button>(R.id.btnSleepCalc)
        val diet = findViewById<Button>(R.id.btnDiet)
        val meditation = findViewById<Button>(R.id.btnMeditation)
        val sleepTracker = findViewById<Button>(R.id.btnSleepTracker)
        val profile = findViewById<Button>(R.id.btnProfile)
        val history = findViewById<Button>(R.id.btnHistory)
        val settings = findViewById<Button>(R.id.btnSettings)

        // Welcome text
        welcomeText.text = "Welcome 👋"

        // Navigation listeners
        chatbot.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        tracker.setOnClickListener { startActivity(Intent(this, TrackerActivity::class.java)) }
        water.setOnClickListener { startActivity(Intent(this, WaterIntakeActivity::class.java)) }
        bmi.setOnClickListener { startActivity(Intent(this, BMICalculatorActivity::class.java)) }
        sleepCalc.setOnClickListener { startActivity(Intent(this, SleepCalculatorActivity::class.java)) }
        diet.setOnClickListener { startActivity(Intent(this, DietPlanActivity::class.java)) }
        meditation.setOnClickListener { startActivity(Intent(this, MeditationTimerActivity::class.java)) }
        sleepTracker.setOnClickListener { startActivity(Intent(this, SleepTrackerActivity::class.java)) }
        profile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        history.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }
        settings.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }
}