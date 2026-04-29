package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_dashboard)

        // ✅ Safe PrefManager usage
        val welcomeText = findViewById<TextView>(R.id.welcome)

        try {
            val pref = PrefManager(this)
            val name = pref.getName()

            welcomeText.text = if (!name.isNullOrEmpty()) {
                "Welcome $name 👋"
            } else {
                "Welcome 👋"
            }

        } catch (e: Exception) {
            welcomeText.text = "Welcome 👋"
        }

        // ✅ Safe button bindings
        val tracker = findViewById<Button>(R.id.btnTracker)
        val profile = findViewById<Button>(R.id.btnProfile)
        val history = findViewById<Button>(R.id.btnHistory)
        val settings = findViewById<Button>(R.id.btnSettings)

        // ✅ Tracker Navigation
        tracker?.setOnClickListener {
            try {
                startActivity(Intent(this, TrackerActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Tracker not available", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Profile Navigation
        profile?.setOnClickListener {
            try {
                startActivity(Intent(this, ProfileActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Profile not available", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ History Navigation
        history?.setOnClickListener {
            try {
                startActivity(Intent(this, HistoryActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "History not available", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Settings Navigation
        settings?.setOnClickListener {
            try {
                startActivity(Intent(this, SettingsActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Settings not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}