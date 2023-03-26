package com.example.retrofitapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofitapp.databinding.PostItemBinding
import com.google.android.material.snackbar.Snackbar

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    class PostViewHolder(val binding: PostItemBinding):
            RecyclerView.ViewHolder(binding.root)
    val differCallback= object :DiffUtil.ItemCallback<PostItem>(){
        override fun areItemsTheSame(p0: PostItem, p1: PostItem): Boolean {
            return p0.id==p1.id
        }

        override fun areContentsTheSame(p0: PostItem, p1: PostItem): Boolean {
            return p0==p1
        }
    }
    val differ=AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PostViewHolder {
        return PostViewHolder(PostItemBinding.inflate(LayoutInflater.from(p0.context),p0,false))
    }

    override fun getItemCount()=differ.currentList.size
    override fun onBindViewHolder(p0: PostViewHolder, p1: Int) {
        val currentPost=differ.currentList[p1]
        p0.binding.apply {
            tvTitle.text=currentPost.title
            tvBody.text=currentPost.body
        }
        p0.itemView.setOnClickListener {
            Snackbar.make(it,"UserId: ${currentPost.id}\n ${currentPost.id}",Snackbar.LENGTH_SHORT).show()
        }
    }
}