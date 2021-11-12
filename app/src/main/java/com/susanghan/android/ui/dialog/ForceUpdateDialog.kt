package com.susanghan.android.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.susanghan.android.databinding.DialogUpdateForceBinding

class ForceUpdateDialog(val onConfirm: (() -> Unit)? = null) :
    BaseBottomSheetDialogFragment<DialogUpdateForceBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogUpdateForceBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.llConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}