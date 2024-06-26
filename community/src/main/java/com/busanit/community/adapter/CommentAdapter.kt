package com.busanit.community.adapter


import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.ConfirmDialog
import com.busanit.community.ConfirmDialogInterface
import com.busanit.community.RetrofitClient
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class CommentAdapter(var comments : List<Comment>) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {
    val TAG = "mylog"

    inner class ItemViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment) {

            binding.commentUser.text = comment.userNickname
            binding.commentContent.text = comment.commentContent
            binding.commentTime.text = comment.commentTime


            val context = binding.root.context
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = ChildrenAdapter(comment.childrenComments)

            val intent = Intent(context, BoardDetailActivity::class.java)
            intent.putExtra("commentId", comment.commentId)

            binding.commentDeleteButton.setOnClickListener {
                RetrofitClient.api.deleteComment(comment.commentId).enqueue(object : Callback<List<Comment>>{
                    override fun onResponse(
                        call: Call<List<Comment>>,
                        response: Response<List<Comment>>
                    ) {
                        if(response.isSuccessful) {
                            Log.d(TAG, "onResponse: ${response.body()}")

                        } else {
                            Log.d(TAG, "onResponse: ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }
                })
            }

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
    fun updateComments(newComment: Comment) {

    }
}