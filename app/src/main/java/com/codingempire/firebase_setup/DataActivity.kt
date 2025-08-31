package com.codingempire.firebase_setup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingempire.firebase_setup.databinding.ActivityDataBinding
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FirebaseFirestore

class DataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataBinding
    private lateinit var db: FirebaseFirestore

    private val courseAdapter = CourseAdapter()
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@DataActivity)
            adapter = courseAdapter
        }


        fetchUsers()
        fetchCourses()
    }

    private fun fetchUsers() {
        db.collection("Users")
            .orderBy("name")
            .get()
            .addOnSuccessListener { snapshots ->
                val items = snapshots.documents.mapNotNull { it.toObject(User::class.java) }
                userAdapter.setData(items)
                binding.tvEmptyUsers.isVisible = items.isEmpty()

            }.addOnFailureListener {
                binding.tvEmptyUsers.isVisible = true
            }

    }

    private fun fetchCourses() {
        db.collection("Courses")
            .orderBy("course")
            .get()
            .addOnSuccessListener { snapshots ->
                val items = snapshots.documents.mapNotNull { it.toObject(Course::class.java) }
                courseAdapter.setData(items)
                binding.tvEmptyCourses.isVisible = items.isEmpty()

            }.addOnFailureListener {
                binding.tvEmptyCourses.isVisible = true
            }

    }


}
