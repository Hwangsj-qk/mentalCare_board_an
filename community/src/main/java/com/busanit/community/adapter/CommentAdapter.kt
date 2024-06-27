package com.busanit.community.adapter


import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.ConfirmDialog
import com.busanit.community.ConfirmDialogInterface
import com.busanit.community.DiffUtilCallback
import com.busanit.community.RetrofitClient
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections.addAll


class CommentAdapter(var comments : List<Comment>) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {
    val TAG = "mylog"
    lateinit var commentAdapter: CommentAdapter

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

//            binding.commentDeleteButton.setOnClickListener {
//                val dialog = ConfirmDialog(BoardDetailActivity(), "댓글을 삭제하시겠습니까?", comment.commentId)
//                dialog.isCancelable = false
//
//            }

        }

//        override fun onYesButtonClick(id: Long) {
//            RetrofitClient.api.deleteComment().enqueue(object : Callback<Comment>{
//                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
//                    if(response.isSuccessful) {
//                        Log.d(TAG, "onResponse: ${response.body()}")
//                        val comment = response.body()!!
//                        val commentMutableList = comments.toMutableList()
//                        commentMutableList.remove(comment)
//                        commentAdapter.updateComments(commentMutableList.toList())
//
//                    } else {
//                        Log.d(TAG, "onResponse: ${response.body()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<Comment>, t: Throwable) {
//                    Log.d(TAG, "onFailure: ${t.message}")
//                }
//            })
//        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    // 시간되면 나중에 수정하기
    fun updateComments(newComments: List<Comment>?) {
        comments = newComments!!
        notifyDataSetChanged()
//        newComments?.let {
//            val diffCallback = DiffUtilCallback(this.comments, newComments)
//            val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//            this.comments.toMutableList().run{
//                clear()
//                addAll(newComments)
//                diffResult.dispatchUpdatesTo(this@CommentAdapter)
//            }
//        }
    }
}