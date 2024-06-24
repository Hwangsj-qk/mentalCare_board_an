package com.busanit.community.fragment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.busanit.community.model.Board
import com.busanit.community.adapter.ItemAdapter
import com.busanit.community.RetrofitClient
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.activity.WriteActivity
import com.busanit.community.databinding.FragmentCommonBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommonFragment : Fragment() {

    lateinit var binding:FragmentCommonBinding
    lateinit var itemAdapter: ItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommonBinding.inflate(inflater, container, false)
        return binding.root



    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = ItemAdapter(listOf(), ::updateBoard, ::deleteBoard)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = itemAdapter


        getBoards()

    }

    // 게시글 목록 가져오기
    private fun getBoards() {
        RetrofitClient.api.boardTagCommon().enqueue(object : Callback<List<Board>> {
            override fun onResponse(call: Call<List<Board>>, response: Response<List<Board>>) {
                if(response.isSuccessful) {
                    val boards = response.body() ?: emptyList()
                    itemAdapter.updateBoards(boards)
                } else {
                    Log.d("mylog", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Board>>, t: Throwable) {
                Log.d("mylog", "onFailure: ${t.message}")
            }

        })
    }


    // 수정 이벤트 핸들러
    private fun updateBoard(board: Board) {

    }

    // 삭제 이벤트 핸들러
    private fun deleteBoard(boardId : Long) {

    }

}