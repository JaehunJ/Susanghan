package com.oldee.expert.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oldee.expert.databinding.DialogPermissionInfoBinding

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