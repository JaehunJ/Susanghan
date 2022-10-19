package com.oldee.expert.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oldee.expert.databinding.DialogUpdateForceBinding

class ForceUpdateDialog(val onConfirm: (() -> Unit)? = null) :
    BaseBottomSheetDialogFragment<DialogUpdateForceBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogUpdateForceBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.btnConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}