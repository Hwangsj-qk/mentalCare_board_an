package com.busanit.community

import com.busanit.community.model.Board
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment
import com.busanit.community.model.HeartResponse
import com.busanit.community.model.Heart
import com.busanit.community.model.NewBoard
import com.busanit.community.model.NewChildren
import com.busanit.community.model.NewComment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/board/TagType/COMMON")
    fun boardTagCommon() : Call<List<Board>>

    @GET("/board/TagType/MENTAL")
    fun boardTagMental() : Call<List<Board>>

    @GET("/board/TagType/CHEERING")
    fun boardTagCheering() : Call<List<Board>>

    @GET("/comment/boardId/{boardId}")
    fun getCommentsByBoardId(@Path("boardId") boardId : Long) : Call<List<Comment>>

    @POST("/board")
    fun createBoard(@Body newBoard: NewBoard) : Call<Board>

    @POST("/boardHeart/up/{boardId}")
    fun upAndDownHeart(@Body heart : Heart, @Path("boardId") boardId: Long) : Call<HeartResponse>

    @POST("/comment")
    fun createComment(@Body newComment: NewComment) : Call<Comment>

    @POST("/children")
    fun createChildren(@Body newChildren: NewChildren) : Call<ChildrenComment>

    @DELETE("/board/delete/{boardId}")
    fun deleteBoard(@Path("boardId") boardId: Long) : Call <Board>

    @DELETE("/comment/{commentId}")
    fun deleteComment(@Path("commentId") commentId: Long) : Call<Comment>

    @PUT("/board/update/{boardId}")
    fun updateBoard(@Body board: Board, @Path("boardId") boardId: Long) : Call<Board>

    @GET("children/commentId/{commentId}")
    fun getChildrenByCommentId(@Path("commentId") commentId : Long) : Call<List<ChildrenComment>>

    @DELETE("children/{childrenId}")
    fun deleteChildrenComment(@Path("childrenId") childrenId :Long) : Call<ChildrenComment>









}