package com.oldee.expert.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oldee.expert.databinding.DialogUpdateNormalBinding

class UpdateDialog(val onCancel: (() -> Unit)? = null, val onConfirm: (() -> Unit)? = null) :
    BaseBottomSheetDialogFragment<DialogUpdateNormalBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogUpdateNormalBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.btnCancel.setOnClickListener {
            dismiss()
            onCancel?.invoke()
        }

        binding.btnConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}