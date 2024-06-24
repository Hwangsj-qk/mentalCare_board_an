package com.busanit.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.Board
import com.busanit.community.model.Comment

class CommentAdapter(var comments : List<Comment>) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentUser.text = comment.userNickname
            binding.commentContent.text = comment.commentContent
            binding.commentTime.text = comment.commentTime
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