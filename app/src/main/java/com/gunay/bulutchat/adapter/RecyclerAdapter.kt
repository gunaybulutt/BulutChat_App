package com.gunay.bulutchat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunay.bulutchat.databinding.RecyclerRowBinding
import com.gunay.bulutchat.model.Post

class RecyclerAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<RecyclerAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.emailAdress.text = postList.get(position).email
        holder.binding.userMesage.text = postList.get(position).message


    }
}