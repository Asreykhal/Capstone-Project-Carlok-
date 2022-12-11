package com.example.carlok.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.carlok.R
import com.example.carlok.admin.JobList
import com.example.carlok.databinding.JobListBinding
import com.squareup.picasso.Picasso

class ListJobAdapter(private val postList: ArrayList<JobList>) : RecyclerView.Adapter<ListJobAdapter.ListViewHolder>() {
    class ListViewHolder(val binding : JobListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = JobListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = postList[position]
        holder.binding.tvJobname.text = postList[position].namaPekerjaan
        holder.binding.tvTempat.text = postList[position].tempatBekerja
        holder.binding.tvKeterangan.text = postList[position].waktuBekerja
        Picasso.get().load(postList[position].imageJob).into(holder.binding.imgJobList)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}