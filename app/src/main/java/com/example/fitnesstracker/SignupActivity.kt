package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)

        // Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Views
        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val backBtn = findViewById<TextView>(R.id.backBtn)

        // Signup Button
        signupBtn.setOnClickListener {

            val nameText = name.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            // Validation
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

            // Firebase Auth Signup
            auth.createUserWithEmailAndPassword(
                emailText,
                passwordText
            ).addOnCompleteListener(this) { task ->

                signupBtn.isEnabled = true

                if (task.isSuccessful) {

                    val userId = auth.currentUser?.uid

                    if (userId != null) {

                        val user = hashMapOf(
                            "name" to nameText,
                            "email" to emailText
                        )

                        // Firestore Save
                        db.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {

                                Toast.makeText(
                                    this@SignupActivity,
                                    "Signup Successful ✅",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Open Login Page
                                val intent = Intent(
                                    this@SignupActivity,
                                    LoginActivity::class.java
                                )

                                startActivity(intent)
                                finish()
                            }

                            .addOnFailureListener {

                                Toast.makeText(
                                    this@SignupActivity,
                                    "Firestore Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }

                } else {

                    Toast.makeText(
                        this@SignupActivity,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        // Back Button
        backBtn.setOnClickListener {

            val intent = Intent(
                this@SignupActivity,
                LoginActivity::class.java
            )

            startActivity(intent)
            finish()
        }
    }
}