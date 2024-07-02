package com.busanit.community.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.busanit.community.RetrofitClient
import com.busanit.community.adapter.BoardAdapter
import com.busanit.community.adapter.ChildrenAdapter
import com.busanit.community.adapter.CommentAdapter
import com.busanit.community.databinding.ActivityBoardDetailBinding
import com.busanit.community.databinding.CommentItemBinding
import com.busanit.community.model.Board
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment
import com.busanit.community.model.Heart
import com.busanit.community.model.HeartResponse
import com.busanit.community.model.NewChildren
import com.busanit.community.model.NewComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "BoardDetailActivity"

class BoardDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoardDetailBinding
    lateinit var commentAdapter: CommentAdapter
    lateinit var comments: List<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val boardId = intent.getLongExtra("boardId", -1)
        val boardTitle = intent.getStringExtra("boardTitle")
        val userNickName = intent.getStringExtra("userNickname")
        val boardTime = intent.getStringExtra("boardTime")
        val boardContent = intent.getStringExtra("boardContent")
        val heartCount = intent.getIntExtra("heartCount", 0)
        val commentCount = intent.getIntExtra("commentCount", 0)
        val boardTag = intent.getStringExtra("boardTag")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        inputBoardText(boardTitle, userNickName, boardTime, boardContent, heartCount, commentCount, boardTag)

        commentAdapter = CommentAdapter()


        RetrofitClient.api.getCommentsByBoardId(boardId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    comments = response.body() ?: emptyList<Comment>().toMutableList()
                    Log.d(TAG, "onResponse: ${response.body()}")
                    binding.recyclerView.adapter = commentAdapter
                    commentAdapter.updateComments(comments)
                } else {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })


        binding.heart.setOnClickListener {
            val heart = Heart(1)

            RetrofitClient.api.upAndDownHeart(heart, boardId)
                .enqueue(object : Callback<HeartResponse> {
                    override fun onResponse(
                        call: Call<HeartResponse>,
                        response: Response<HeartResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@BoardDetailActivity, "공감버튼 클릭", Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, "onResponse: ${response.body()}")
                            val count = response.body()?.count
                            binding.heartCount.text = count.toString()
                        }
                    }

                    override fun onFailure(call: Call<HeartResponse>, t: Throwable) {
                        Toast.makeText(
                            this@BoardDetailActivity,
                            "연결 실패 ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onFailure: ${t.message} ${t.printStackTrace()}")
                    }
                })
        }

        binding.commentButton.setOnClickListener {
            val commentContent = binding.commentContent.text.toString()
            val newComment = NewComment(commentContent, "마이콜", boardId)

            RetrofitClient.api.createComment(newComment).enqueue(object : Callback<Comment> {
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@BoardDetailActivity, "새로운 댓글 작성", Toast.LENGTH_SHORT)
                            .show()
                        binding.commentContent.text.clear()
                        val comment = response.body()!!
                        val commentMutableList = comments.toMutableList()
                        commentMutableList.add(comment)
                        commentAdapter.updateComments(commentMutableList.toList())
                        Log.d(TAG, "onResponse: ${response.body()}")
                        binding.commentCount.text =
                            (intent.getIntExtra("commentCount", 0) + 1).toString()

                    } else {
                        Log.d(TAG, "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }

        binding.boardDeleteButton.setOnClickListener {
            RetrofitClient.api.deleteBoard(boardId)
                .enqueue(object : Callback<Board> {
                    override fun onResponse(call: Call<Board>, response: Response<Board>) {
                        if (response.isSuccessful) {
                            val resultIntent = Intent().apply {
                                putExtra("deletedBoardId", boardId)
                            }
                            setResult(Activity.RESULT_OK, resultIntent)
                            Toast.makeText(
                                this@BoardDetailActivity,
                                "게시글이 성공적으로 삭제되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d(TAG, "onResponse: ${response.body()}")
                            finish()
                        } else {
                            Log.d(TAG, "onResponse: ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<Board>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }
                })

        }


        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                RetrofitClient.api.getBoardBYId(boardId).enqueue(object : Callback<Board> {
                    override fun onResponse(
                        call: Call<Board>,
                        response: Response<Board>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "onResponse: 응답 성공 ${response.body()}")
                            inputBoardText(response.body()?.boardTitle, userNickName, boardTime, response.body()?.boardContent,
                                heartCount, commentCount, response.body()?.boardTag)
                        } else {
                            Log.d(TAG, "onResponse: 응답 실패 ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<Board>, t: Throwable) {
                        Log.d(TAG, "onFailure: 연결 실패 ${t.message}")
                    }
                })
            }
        }

        binding.boardModifyButton.setOnClickListener {
            val boardTitle = intent.getStringExtra("boardTitle")
            val boardContent = intent.getStringExtra("boardContent")
            val userNickname = intent.getStringExtra("userNickname")
            val boardTag = intent.getStringExtra("boardTag")
            val boardLikeCount = intent.getIntExtra("boardLikeCount", -1)
            val boardCommentCount = intent.getIntExtra("boardCommentCount", -1)
            val calculateTime = intent.getStringExtra("calculateTime")

            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("boardId", boardId)
            intent.putExtra("boardTitle", boardTitle)
            intent.putExtra("boardContent", boardContent)
            intent.putExtra("userNickname", userNickname)
            intent.putExtra("boardTag", boardTag)
            intent.putExtra("boardLikeCount", boardLikeCount)
            intent.putExtra("boardCommentCount", boardCommentCount)
            intent.putExtra("calculateTime", calculateTime)
            activityResultLauncher.launch(intent)

        }
    }

    private fun inputBoardText(
        boardTitle: String?,
        userNickName: String?,
        boardTime: String?,
        boardContent: String?,
        heartCount: Int?,
        commentCount: Int?,
        boardTag : String?
    ) {
        binding.boardTitle.text = boardTitle
        binding.userNickName.text = userNickName
        binding.boardTime.text = boardTime
        binding.BoardContent.text = boardContent
        binding.heartCount.text = heartCount.toString()
        binding.commentCount.text = commentCount.toString()

        if (boardTag == "COMMON") {
            binding.boardTag.text = "일반고민 게시판"
        } else if (boardTag == "MENTAL") {
            binding.boardTag.text = "정신건강 게시판"
        } else {
            binding.boardTag.text = "응원 게시판"
        }

    }

}
