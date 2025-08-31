package com.codingempire.firebase_setup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codingempire.firebase_setup.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvsignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = db.collection("Users")
            user.whereEqualTo("email", email).whereEqualTo("password", password)
                .limit(1).get().addOnSuccessListener { q ->
                    if (q.isEmpty) {
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    } else {
                        val doc = q.documents.first()
                        val newUser = doc.toObject<User>()!!.copy(id = doc.id)

                        SharedPrefs(this).saveUser(newUser)

                        val i = Intent(this, HomeActivity::class.java)
                        i.putExtra("user", newUser)
                        startActivity(i)
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Login Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}