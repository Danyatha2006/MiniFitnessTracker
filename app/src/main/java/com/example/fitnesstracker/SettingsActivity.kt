package com.example.fitnesstracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_settings)

        val pref = PrefManager(this)

        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        resetBtn.setOnClickListener {
            pref.saveSteps(0)
            pref.saveCalories(0)
            Toast.makeText(this, "Data Reset", Toast.LENGTH_SHORT).show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}