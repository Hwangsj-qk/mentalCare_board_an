package com.busanit.community.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.busanit.community.R
import com.busanit.community.RetrofitClient
import com.busanit.community.adapter.BoardAdapter
import com.busanit.community.databinding.ActivityWriteBinding
import com.busanit.community.model.Board
import com.busanit.community.model.NewBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteBinding
    lateinit var boards: List<Board>
    lateinit var boardAdapter: BoardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.run {
            completeButton.setOnClickListener {
                val title = editTitle.text.toString()
                val content = editContent.text.toString()
                var tag = "COMMON"

                when(tagGroup.checkedRadioButtonId) {
                    R.id.commonButton -> tag = "COMMON"
                    R.id.mentalButton -> tag = "MENTAL"
                    R.id.cheeringButton -> tag = "CHEERING"
                }

                val newBoard = NewBoard(tag, title, content, "희동이")

                RetrofitClient.api.createBoard(newBoard).enqueue(object : Callback<Board> {
                    override fun onResponse(call: Call<Board>, response: Response<Board>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@WriteActivity, "새로운 게시글 작성", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("mylog", "onResponse: ${response.body()}")
                            val board = response.body()!!
                            val boardMutableList = boards.toMutableList()
                            boardMutableList.add(board)
                            boardAdapter.updateBoards(boardMutableList.toList())
                            finish()
                        } else {
                            Toast.makeText(this@WriteActivity, "응답 실패 ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Board>, t: Throwable) {
                        Log.d("mylog", "onFailure: ${t.message}")
                    }
                })

            }

            binding.editTitle.setText(intent.getStringExtra("boardTitle"))


        }
    }
}