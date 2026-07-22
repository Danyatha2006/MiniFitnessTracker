package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BMICalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bmi_calculator)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val btnCalculate = findViewById<Button>(R.id.btnCalculateBMI)
        val resultText = findViewById<TextView>(R.id.bmiResultText)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnCalculate.setOnClickListener {
            val weightStr = weightInput.text.toString()
            val heightStr = heightInput.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                val weight = weightStr.toFloat()
                val height = heightStr.toFloat() / 100 // cm to m
                val bmi = weight / (height * height)
                
                val category = when {
                    bmi < 18.5 -> "Underweight"
                    bmi < 25 -> "Normal"
                    bmi < 30 -> "Overweight"
                    else -> "Obese"
                }
                
                resultText.text = "Result: %.2f\n($category)".format(bmi)
            } else {
                Toast.makeText(this, "Please enter weight and height", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}