package com.susanghan.android.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.susanghan.android.databinding.DialogUpdateNormalBinding

class UpdateDialog(val onCancel: (() -> Unit)? = null, val onConfirm: (() -> Unit)? = null) :
    BaseBottomSheetDialogFragment<DialogUpdateNormalBinding>() {
    override val bindingInflater = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
        DialogUpdateNormalBinding.inflate(layoutInflater, viewGroup, b)
    }

    override fun setEvent() {
        binding.llCancel.setOnClickListener {
            dismiss()
            onCancel?.invoke()
        }

        binding.llConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}