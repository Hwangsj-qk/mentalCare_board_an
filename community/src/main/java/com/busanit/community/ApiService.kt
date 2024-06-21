package com.busanit.community

import com.busanit.community.model.Board
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/board/TagType/COMMON")
    fun boardTagCommon() : Call<List<Board>>

    @GET("/board/TagType/MENTAL")
    fun boardTagMental() : Call<List<Board>>

    @GET("/board/TagType/CHEERING")
    fun boardTagCheering() : Call<List<Board>>

}