package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WaterIntakeActivity : AppCompatActivity() {
    private var waterCount = 0
    private lateinit var countText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_water_intake)

        countText = findViewById(R.id.waterCountText)
        val btnAdd = findViewById<Button>(R.id.btnAddWater)
        val btnReset = findViewById<Button>(R.id.btnResetWater)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnAdd.setOnClickListener {
            waterCount++
            updateUI()
        }

        btnReset.setOnClickListener {
            waterCount = 0
            updateUI()
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun updateUI() {
        countText.text = "$waterCount Glasses"
    }
}