package com.example.fitnesstracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var pref: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val bioInput = findViewById<EditText>(R.id.bioInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Initialize PrefManager
        pref = PrefManager(this)

        // Load saved profile
        usernameInput.setText(pref.getUsername())
        bioInput.setText(pref.getBio())

        // Save profile button
        saveBtn.setOnClickListener {
            val nameText = usernameInput.text.toString().trim()
            val bioText = bioInput.text.toString().trim()

            if (nameText.isEmpty()) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                pref.setUsername(nameText)
                pref.setBio(bioText)

                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Error saving profile", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        // Back button
        backBtn.setOnClickListener {
            finish()
        }
    }
}