package com.busanit.community.model

data class NewComment(
    val commentContent : String,
    val userNickname : String, val boardId : Long
)
