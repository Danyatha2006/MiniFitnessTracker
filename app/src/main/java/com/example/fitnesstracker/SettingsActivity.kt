package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var pref: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.hide()

        pref = PrefManager(this)

        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        resetBtn.setOnClickListener {

            pref.resetTodayWorkout()

            Toast.makeText(
                this,
                "Today's workout reset successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}