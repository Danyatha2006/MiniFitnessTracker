package com.example.fitnesstracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CaloriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_calories)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val activeInput = findViewById<EditText>(R.id.activeInput)
        val calcBtn = findViewById<Button>(R.id.calcBtn)
        val result = findViewById<TextView>(R.id.result)
        val backBtn = findViewById<Button>(R.id.backBtn)

        calcBtn.setOnClickListener {

            val weightText = weightInput.text.toString().trim()
            val activeText = activeInput.text.toString().trim()

            if (weightText.isEmpty() || activeText.isEmpty()) {
                result.text = "Enter valid values"
                return@setOnClickListener
            }

            val weight = weightText.toFloatOrNull()
            val minutes = activeText.toFloatOrNull()

            if (weight != null && minutes != null) {
                val calories = (weight * minutes * 0.08f).toInt()
                result.text = "$calories kcal"

                // safe save
                getSharedPreferences("fitness", MODE_PRIVATE)
                    .edit()
                    .putInt("calories", calories)
                    .apply()

            } else {
                result.text = "Enter valid values"
            }
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}