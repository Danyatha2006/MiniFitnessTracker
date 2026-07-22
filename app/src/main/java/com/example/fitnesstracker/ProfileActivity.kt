package com.example.fitnesstracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView

    private lateinit var uploadBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var editBtn: Button
    private lateinit var backBtn: Button

    private lateinit var username: EditText
    private lateinit var bio: EditText
    private lateinit var email: EditText
    private lateinit var weight: EditText
    private lateinit var height: EditText

    private val PICK_IMAGE = 1
    private var imageUriString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profileImage)

        uploadBtn = findViewById(R.id.btnUpload)
        saveBtn = findViewById(R.id.btnSave)
        editBtn = findViewById(R.id.btnEdit)
        backBtn = findViewById(R.id.btnBack)

        username = findViewById(R.id.etUsername)
        bio = findViewById(R.id.etBio)
        email = findViewById(R.id.etEmail)
        weight = findViewById(R.id.etWeight)
        height = findViewById(R.id.etHeight)

        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        // Load text data
        username.setText(sharedPref.getString("username", ""))
        bio.setText(sharedPref.getString("bio", ""))
        email.setText(sharedPref.getString("email", ""))
        weight.setText(sharedPref.getString("weight", ""))
        height.setText(sharedPref.getString("height", ""))

        // Load image safely
        imageUriString = sharedPref.getString("image", null)
        imageUriString?.let {
            try {
                profileImage.setImageURI(Uri.parse(it))
            } catch (e: Exception) {
                profileImage.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }

        // Upload Image (OLD METHOD - SAFE)
        uploadBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Save Profile
        saveBtn.setOnClickListener {

            val editor = sharedPref.edit()

            editor.putString("username", username.text.toString())
            editor.putString("bio", bio.text.toString())
            editor.putString("email", email.text.toString())
            editor.putString("weight", weight.text.toString())
            editor.putString("height", height.text.toString())

            editor.putString("image", imageUriString)

            editor.apply()

            Toast.makeText(
                this,
                "Profile Saved Successfully ✅",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Edit Profile
        editBtn.setOnClickListener {

            username.isEnabled = true
            bio.isEnabled = true
            email.isEnabled = true
            weight.isEnabled = true
            height.isEnabled = true

            Toast.makeText(
                this,
                "Edit Mode Enabled ✏️",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Back
        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (
            requestCode == PICK_IMAGE &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            val imageUri: Uri? = data.data

            if (imageUri != null) {
                profileImage.setImageURI(imageUri)
                imageUriString = imageUri.toString()
            }
        }
    }
}