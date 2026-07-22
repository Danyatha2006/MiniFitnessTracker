package com.example.fitnesstracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DietPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_diet_plan)

        val btnGenerate = findViewById<Button>(R.id.btnGeneratePlan)
        val dietPlanText = findViewById<TextView>(R.id.dietPlanText)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val plans = arrayOf(
            "• Breakfast: Oatmeal with berries\n• Lunch: Grilled chicken salad\n• Snack: Greek yogurt\n• Dinner: Baked salmon with broccoli",
            "• Breakfast: Scrambled eggs with spinach\n• Lunch: Quinoa bowl with chickpeas\n• Snack: Apple with almond butter\n• Dinner: Turkey stir-fry with veggies",
            "• Breakfast: Greek yogurt parfait\n• Lunch: Tuna wrap with sprouts\n• Snack: Mixed nuts\n• Dinner: Lean beef with sweet potato"
        )

        btnGenerate.setOnClickListener {
            dietPlanText.text = plans.random()
        }

        btnBack.setOnClickListener { finish() }
    }
}