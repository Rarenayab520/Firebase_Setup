package com.codingempire.firebase_setup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codingempire.firebase_setup.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = db.collection("Users")
            user.whereEqualTo("email", email).limit(1).get()
                .addOnSuccessListener { q ->
                    if (!q.isEmpty) {
                        Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show()
                    } else {
                        val temp = User(
                            id = "",
                            name = name,
                            email = email,
                            password = password,
                            createdAt = System.currentTimeMillis(),
                            isLoggedIn = true
                        )
                        user.add(temp).addOnSuccessListener { doc ->
                            val finalUser = temp.copy(id = doc.id)
                            doc.update(mapOf("id" to doc.id, "isLoggedIn" to true))
                                .addOnSuccessListener {
                                    val i = Intent(this, MainActivity::class.java)
                                    i.putExtra("user", finalUser)
                                    startActivity(i)
                                    finish()
                                }.addOnFailureListener { e ->
                                Toast.makeText(
                                    this, "Signup Failed: ${e.message}", Toast.LENGTH_SHORT
                                ).show()
                            }

                        }.addOnFailureListener { e ->

                            Toast.makeText(this, "Error : ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

        }

    }
}