package com.susanghan.android.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.susanghan.android.databinding.DialogPermissionInfoBinding

class PermissionDialog(val onConfirm: (() -> Unit)? = null) :
    BaseDialogFragment<DialogPermissionInfoBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogPermissionInfoBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setConfirmEvent() {
        binding.llConfirm.setOnClickListener {
            dismiss()
            onConfirm?.invoke()
        }
    }
}