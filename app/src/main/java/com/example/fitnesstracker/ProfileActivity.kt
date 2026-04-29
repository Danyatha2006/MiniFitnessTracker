package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_profile)

        val pref = PrefManager(this)

        val name = findViewById<TextView>(R.id.name)
        val username = findViewById<TextView>(R.id.username)
        val bio = findViewById<TextView>(R.id.bio)
        val logout = findViewById<Button>(R.id.logout)
        val backBtn = findViewById<Button>(R.id.backBtn)
        val editBtn = findViewById<Button>(R.id.editBtn)

        name.text = pref.getName()
        username.text = pref.getUsername()
        bio.text = pref.getBio()

        logout.setOnClickListener {
            pref.clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        backBtn.setOnClickListener {
            finish()
        }

        editBtn.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }
}