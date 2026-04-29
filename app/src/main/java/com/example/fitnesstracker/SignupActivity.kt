package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        // 🔥 Hide ActionBar (for your UI design)
        supportActionBar?.hide()

        // Firebase init
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Views
        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val backBtn = findViewById<TextView>(R.id.backBtn)

        signupBtn.setOnClickListener {

            val nameText = name.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            // ✅ VALIDATIONS (IMPORTANT)
            if (nameText.isEmpty()) {
                name.error = "Enter name"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                email.error = "Invalid email"
                return@setOnClickListener
            }

            if (passwordText.length < 6) {
                password.error = "Password must be 6+ characters"
                return@setOnClickListener
            }

            signupBtn.isEnabled = false

            auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->

                    signupBtn.isEnabled = true

                    if (task.isSuccessful) {

                        val userId = auth.currentUser?.uid

                        if (userId == null) {
                            Toast.makeText(this, "User error", Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }

                        val user = hashMapOf(
                            "name" to nameText,
                            "email" to emailText
                        )

                        db.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {

                                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Firestore Error", Toast.LENGTH_SHORT).show()
                            }

                    } else {

                        // 🔥 BETTER ERROR HANDLING
                        val message = when {
                            task.exception?.message?.contains("email address is already in use") == true ->
                                "Email already exists"

                            task.exception?.message?.contains("badly formatted") == true ->
                                "Invalid email format"

                            else -> "Signup Failed"
                        }

                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        backBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}