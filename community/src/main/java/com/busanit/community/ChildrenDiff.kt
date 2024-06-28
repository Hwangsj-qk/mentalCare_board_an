package com.busanit.community

import androidx.recyclerview.widget.DiffUtil
import com.busanit.community.model.ChildrenComment
import com.busanit.community.model.Comment

class ChildrenDiff(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is ChildrenComment && newItem is ChildrenComment) {
            oldItem.childrenId == newItem.childrenId
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]


    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}