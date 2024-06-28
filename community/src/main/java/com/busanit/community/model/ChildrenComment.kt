package com.busanit.community.model

data class ChildrenComment (
    val commentId : Long,
    val childrenId : Long,
    val childrenContent: String,
    val childrenTime: String,
    val userNickname : String)