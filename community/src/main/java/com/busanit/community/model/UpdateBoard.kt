package com.busanit.community.model

data class UpdateBoard (val boardTitle: String,
                   val boardContent: String,
                   val boardLikeCount : Int,
                   val boardCommentCount : Int,
                   val calculateTime : String,
                   val userNickname: String,
                   val boardTag: String,
                   val boardId: Long,
                   val comments : List<Comment>
)
