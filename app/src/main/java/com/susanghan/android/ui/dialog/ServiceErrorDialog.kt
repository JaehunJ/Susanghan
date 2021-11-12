package com.susanghan.android.ui.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.susanghan.android.databinding.DialogServiceErrorBinding

class ServiceErrorDialog(
    val onConfirm: (() -> Unit)? = null
) : BaseBottomSheetDialogFragment<DialogServiceErrorBinding>() {
    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogServiceErrorBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setEvent() {
        binding.llConfirm.setOnClickListener {
            dismiss()
            Log.e("#debug", "click confirm")
            onConfirm?.invoke()
        }
    }
}