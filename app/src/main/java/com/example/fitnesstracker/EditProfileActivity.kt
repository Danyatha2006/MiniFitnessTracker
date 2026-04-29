package com.example.fitnesstracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)

        val username = findViewById<EditText>(R.id.usernameInput)
        val bio = findViewById<EditText>(R.id.bioInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // ✅ Safe PrefManager usage
        try {
            val pref = PrefManager(this)

            val savedUsername = pref.getUsername()
            val savedBio = pref.getBio()

            username.setText(savedUsername ?: "")
            bio.setText(savedBio ?: "")

        } catch (e: Exception) {
            e.printStackTrace()
        }

        // ✅ Save Profile (safe)
        saveBtn.setOnClickListener {

            val nameText = username.text.toString().trim()
            val bioText = bio.text.toString().trim()

            if (nameText.isEmpty()) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val pref = PrefManager(this)
                pref.saveProfile(nameText, bioText)

                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                finish()

            } catch (e: Exception) {
                Toast.makeText(this, "Error saving profile", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔙 Back Button
        backBtn.setOnClickListener {
            finish()
        }
    }
}