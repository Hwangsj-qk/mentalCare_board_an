package com.busanit.community.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.databinding.BoardItemBinding
import com.busanit.community.model.Board


class ItemAdapter(var boards: List<Board>,
                  val onEdit : (Board) -> Unit,     // 수정 이벤트 핸들러
                  val onDelete : (Long) -> Unit     // 삭제 이벤트 핸들러
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder> () {

    // 매개변수로 항목을 레이아웃 뷰 바인딩을 삽입
    inner class ItemViewHolder(val binding: BoardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(board: Board) {
            binding.boardTitle.text = board.boardTitle
            binding.boardContent.text = board.boardContent
            binding.boardTime.text = board.calculateTime
            binding.userNickName.text = board.userNickname
            binding.heartCount.text = board.boardLikeCount.toString()
            binding.commentCount.text = board.boardCommentCount.toString()

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, BoardDetailActivity::class.java)
                intent.putExtra("boardId", board.boardId)
                intent.putExtra("boardTitle", board.boardTitle)
                intent.putExtra("boardContent", board.boardContent)
                intent.putExtra("userNickname", board.userNickname)
                intent.putExtra("heartCount", board.boardLikeCount)
                intent.putExtra("commentCount", board.boardCommentCount)
                intent.putExtra("boardTag", board.boardTag)
                intent.putExtra("boardTime", board.calculateTime)
                context.startActivity(intent)
            }

        }
    }

    // 어댑터의 메서드 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val binding = BoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // 데이터와 뷰홀더 바인딩
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(boards[position])

//        // BoardDetailActivity로 이동
//        holder.itemView.setOnClickListener{
//            val intent = Intent(holder.itemView?.context, BoardDetailActivity::class.java)
//            val board = boards[position]
//            intent.putExtra("board", board)
//            ContextCompat.startActivity(holder.itemView.context, intent, null)
//        }


    }

    // 데이터의 개수
    override fun getItemCount(): Int = boards.size

    // 게시글 목록 갱신
    fun updateBoards(newBoards : List<Board>) {
        boards = newBoards
        notifyDataSetChanged()
    }

    fun getBoardTag(newBoards: List<Board>) {
        for(board in newBoards) {
            board.boardTag
        }
    }



}