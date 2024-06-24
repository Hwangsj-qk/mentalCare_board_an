package com.busanit.community

import com.busanit.community.model.Board
import com.busanit.community.model.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/board/TagType/COMMON")
    fun boardTagCommon() : Call<List<Board>>

    @GET("/board/TagType/MENTAL")
    fun boardTagMental() : Call<List<Board>>

    @GET("/board/TagType/CHEERING")
    fun boardTagCheering() : Call<List<Board>>

    @GET("/board")
    fun getAllBoards() : Call<List<Board>>

    @GET("/comment")
    fun getCommentsByBoardId(@Query("boardId") boardId : Long) : Call<List<Comment>>

}