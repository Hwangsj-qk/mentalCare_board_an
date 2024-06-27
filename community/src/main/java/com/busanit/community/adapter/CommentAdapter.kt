package com.busanit.community.adapter


import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.CommentDiff
import com.busanit.community.ConfirmDialog
import com.busanit.community.ConfirmDialogInterface
import com.busanit.community.DiffUtilCallback
import com.busanit.community.R
import com.busanit.community.RetrofitClient
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.databinding.ActivityBoardDetailBinding
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.Board
import com.busanit.community.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections.addAll


class CommentAdapter: RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {
    val TAG = "mylog"
    private var comments = mutableListOf<Comment>()
    lateinit var boardAdapter: BoardAdapter

    inner class ItemViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentUser.text = comment.userNickname
            binding.commentContent.text = comment.commentContent
            binding.commentTime.text = comment.commentTime


            val context = binding.root.context
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = ChildrenAdapter(comment.childrenComments)

            // 삭제 시 board의 commentCount는 선생님께 여쭤보기ㅠㅠㅠ
            binding.commentDeleteButton.setOnClickListener {
                RetrofitClient.api.deleteComment(comment.commentId).enqueue(object : Callback<Comment> {
                    override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                        if(response.isSuccessful) {
                            removeByCommentId(comment.commentId)
                            Log.d(TAG, "onResponse: ${response.body()}")
                            Toast.makeText(it.context, "댓글이 정상적으로 삭제되었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Comment>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }
                })

            }

            binding.childrenButton.setOnClickListener {
                val commentIntent = Intent(context, BoardDetailActivity::class.java)
                commentIntent.putExtra("commentId", comment.commentId)
                binding.root.context.startActivity(commentIntent)
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


    fun updateComments(newComments: List<Comment>?) {
        newComments?.let {
            val diffCallback = CommentDiff(this.comments, newComments)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.comments.run {
                clear()
                addAll(newComments)
                diffResult.dispatchUpdatesTo(this@CommentAdapter)
            }
        }
    }

    fun removeByCommentId(commentId: Long) {
        val position = comments.indexOfFirst { it.commentId == commentId }
        val binding = ActivityBoardDetailBinding.inflate(LayoutInflater.from(BoardDetailActivity()))
        if (position != -1) {
            comments.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}