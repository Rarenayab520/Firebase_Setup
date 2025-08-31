package com.codingempire.firebase_setup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingempire.firebase_setup.databinding.ItemCourseBinding

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.VH>() {

    private val items = mutableListOf<Course>()

    fun setData(newItems: List<Course>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class VH(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.VH {
        val b = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: CourseAdapter.VH, position: Int) {
        val item = items[position]

        holder.binding.tvCourse.text = item.course
        holder.binding.tvRollno.text = item.rollNo
        holder.binding.tvCreditHours.text = item.creditHours
        holder.binding.tvTeacherName.text = item.teacherName
    }

    override fun getItemCount() = items.size
}