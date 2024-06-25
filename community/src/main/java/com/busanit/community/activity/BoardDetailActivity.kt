package com.busanit.community.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.busanit.community.RetrofitClient
import com.busanit.community.adapter.ChildrenAdapter
import com.busanit.community.adapter.CommentAdapter
import com.busanit.community.databinding.ActivityBoardDetailBinding
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment
import com.busanit.community.model.Heart
import com.busanit.community.model.HeartResponse
import com.busanit.community.model.NewComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

private const val TAG = "BoardDetailActivity"
class BoardDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoardDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val boardTag = intent.getStringExtra("boardTag")

        if (boardTag == "COMMON") {
            binding.boardTag.text = "일반고민 게시판"
        } else if (boardTag == "MENTAL") {
            binding.boardTag.text = "정신건강 게시판"
        } else {
            binding.boardTag.text = "응원 게시판"
        }

        binding.boardTitle.text = intent.getStringExtra("boardTitle")
        binding.userNickName.text = intent.getStringExtra("userNickname")
        binding.boardTime.text = intent.getStringExtra("boardTime")
        binding.BoardContent.text = intent.getStringExtra("boardContent")
        binding.heartCount.text = intent.getIntExtra("heartCount", 0).toString()
        binding.commentCount.text = intent.getIntExtra("commentCount", 0).toString()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val boardId = intent.getLongExtra("boardId", -1)

        RetrofitClient.api.getCommentsByBoardId(boardId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(response.isSuccessful) {
                    val comments = response.body() ?: emptyList()
                    binding.recyclerView.adapter = CommentAdapter(comments)
                }
            }
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
        
        binding.heart.setOnClickListener { 
            val heart = Heart(1)

            RetrofitClient.api.upAndDownHeart(heart, boardId).enqueue(object : Callback<HeartResponse> {
                override fun onResponse(call: Call<HeartResponse>, response: Response<HeartResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@BoardDetailActivity, "공감버튼 클릭", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse: ${response.body()}")
                        val count = response.body()?.count
                        binding.heartCount.text = count.toString()
                    }
                }

                override fun onFailure(call: Call<HeartResponse>, t: Throwable) {
                    Toast.makeText(this@BoardDetailActivity, "연결 실패 ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onFailure: ${t.message} ${t.printStackTrace()}")
                }
            })
        }

        binding.commentButton.setOnClickListener {
            val commentContent = binding.commentContent.text.toString()
            val newComment = NewComment(commentContent, "아기공룡 둘리", boardId)

            RetrofitClient.api.createComment(newComment).enqueue(object : Callback<Comment> {
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@BoardDetailActivity, "새로운 댓글 작성", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, "onResponse: ${response.body()}")
                        binding.commentCount.text = (intent.getIntExtra("commentCount", 0) + 1).toString()
                    } else {
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }








    }


}
