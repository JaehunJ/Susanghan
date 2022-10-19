package com.oldee.expert.ui.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.oldee.expert.databinding.DialogServiceErrorBinding

class ServiceErrorDialog(
    val onConfirm: (() -> Unit)? = null
) : BaseBottomSheetDialogFragment<DialogServiceErrorBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogServiceErrorBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.btnConfirm.setOnClickListener {
            dismiss()
            Log.e("#debug", "click confirm")
            onConfirm?.invoke()
        }
    }
}