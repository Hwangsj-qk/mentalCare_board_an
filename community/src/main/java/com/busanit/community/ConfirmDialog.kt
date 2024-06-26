package com.busanit.community

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.busanit.community.activity.BoardDetailActivity
import com.busanit.community.databinding.DialogDeleteboardBinding

class ConfirmDialog(
    confirmDialogInterface: BoardDetailActivity,
    text: String, id: Long
) : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: DialogDeleteboardBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null

    private var text: String? = null
    private var id: Long? = null

    init {
        this.text = text
        this.id = id
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDeleteboardBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.confirmTextView.text = text

        // 취소 버튼 클릭
        binding.noButton.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.yesButton.setOnClickListener {
            this.confirmDialogInterface?.onYesButtonClick(id!!)
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface ConfirmDialogInterface {
    fun onYesButtonClick(id: Long)
}