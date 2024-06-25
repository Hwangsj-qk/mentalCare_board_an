package com.busanit.community.model

data class Comment (val boardId : Long,
                    val commentContent: String,
                    val commentTime: String,
                    val userNickname : String,
                    val commentId : Long)