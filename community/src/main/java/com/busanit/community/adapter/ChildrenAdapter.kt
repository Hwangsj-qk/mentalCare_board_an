package com.busanit.community.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.ChildrenComment

class ChildrenAdapter(var children : List<ChildrenComment>) : RecyclerView.Adapter<ChildrenAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(children: ChildrenComment) {
            binding.commentUser.text = children.userNickname
            binding.commentContent.text = children.childrenContent
            binding.commentTime.text = children.childrenTime
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = children.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(children[position])
    }

    // 댓글 목록 갱신
    fun updateChildren(newChildren: List<ChildrenComment>) {
        children = newChildren
        notifyDataSetChanged()
    }
}