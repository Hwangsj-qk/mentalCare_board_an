package com.busanit.community.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime


data class Board(val boardTitle: String,
                 val boardContent: String,
                 val boardLikeCount : Int,
                 val boardCommentCount : Int,
                 val calculateTime : String,
                 val userNickname: String,
                 val boardTag: String,
                 val boardId: Long,
                 val comments : List<Comment>)
