package com.codingempire.firebase_setup

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codingempire.firebase_setup.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as? User ?: SharedPrefs(this).getUser()

        binding.tvRecieved.text = "WELCOME , ${user?.name ?: "Guest"}"





        binding.btnLogout.setOnClickListener {
            SharedPrefs(this).clearUser()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}