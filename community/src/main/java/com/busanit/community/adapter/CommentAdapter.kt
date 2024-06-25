package com.busanit.community.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.RetrofitClient
import com.busanit.community.databinding.ActivityBoardDetailBinding
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment
import com.busanit.community.model.NewChildren
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentAdapter(var comments : List<Comment>) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {

            binding.commentUser.text = comment.userNickname
            binding.commentContent.text = comment.commentContent
            binding.commentTime.text = comment.commentTime


            val context = binding.root.context
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = ChildrenAdapter(comment.childrenComments)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    // 댓글 목록 갱신
    fun updateComments(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged()
    }

}