package com.busanit.community

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.busanit.community.databinding.BoardItemBinding

class BoardAdapter(val itemList: MutableList<Item>) :
    RecyclerView.Adapter<BoardAdapter.ItemViewHolder>() {

    // 5. 뷰 홀더 작성하기
    // 매개변수로 항목의 레이아웃 뷰 바인딩을 삽입
    inner class ItemViewHolder(val binding: BoardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // 6. 어댑터의 메서드 구현
    // 6-1. onCreateViewHolder : 뷰 홀더를 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = BoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // 6-2. getItemCount : 데이터의 개수
    override fun getItemCount(): Int = itemList.size

    // 6-3. onBindViewHolder : 데이터와 뷰홀더 바인딩
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.run {

        }
    }
}