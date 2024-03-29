package com.oldee.expert.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oldee.expert.databinding.DialogServiceCheckBinding

class ServiceCheckDialog(
    val onConfirm: (() -> Unit)? = null
) : BaseBottomSheetDialogFragment<DialogServiceCheckBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogServiceCheckBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.llConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}