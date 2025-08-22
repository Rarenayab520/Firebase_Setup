package com.codingempire.firebase_setup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codingempire.firebase_setup.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("user") as? User ?: SharedPrefs(this).getUser()

        binding.tvRecieved.text = "WELCOME , ${user?.name ?: "Guest"}"


       binding.btnSave.setOnClickListener {
           val rollNo = binding.etRollNo.text.toString().trim()
           val course = binding.etCourse.text.toString().trim()
           val creditHours = binding.etCreditHours.text.toString().trim()
           val teacherName = binding.etCourseTeacher.text.toString().trim()

           if(rollNo.isEmpty() || course.isEmpty() || creditHours.isEmpty() || teacherName.isEmpty()){
               Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }

           val newCourse = Course(rollNo,course,creditHours,teacherName)

        val savedCourse =  db.collection("Courses")
           savedCourse.add(newCourse)
               .addOnSuccessListener {
                   Toast.makeText(this,"Course Data Saved",Toast.LENGTH_SHORT).show()
               }.addOnFailureListener {
                   Toast.makeText(this,"Error...!Course Data not Saved",Toast.LENGTH_SHORT).show()
               }

       }


        binding.btnLogout.setOnClickListener {
            SharedPrefs(this).clearUser()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}