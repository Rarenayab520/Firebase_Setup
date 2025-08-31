package com.codingempire.firebase_setup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingempire.firebase_setup.databinding.ActivityHomeBinding
import com.codingempire.firebase_setup.databinding.ItemUserBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAdapter : RecyclerView.Adapter<UserAdapter.VH>() {

    private val items = mutableListOf<User>()

    fun setData(newItems : List<User>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class VH (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.VH {
        val b = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH (b)
    }

    override fun onBindViewHolder(holder: UserAdapter.VH, position: Int) {
        val item = items [position]

        holder.binding.tvUserName.text = item.name
        holder.binding.tvUserEmail.text = item.email
        holder.binding.tvUserPassword.text = item.password
        holder.binding.tvUserCreatedAt.text =
            SimpleDateFormat("yyyy-MM-dd HH:mm ", Locale.getDefault()).format(Date(item.createdAt))

    }

    override fun getItemCount() = items.size


}