package com.busanit.community.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.busanit.community.RetrofitClient
import com.busanit.community.adapter.ChildrenAdapter
import com.busanit.community.adapter.CommentAdapter
import com.busanit.community.databinding.ActivityBoardDetailBinding
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val commentId = intent.getLongExtra("commentId", -1)





    }


}
