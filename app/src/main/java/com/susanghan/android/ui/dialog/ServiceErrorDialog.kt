package com.susanghan.android.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.susanghan.android.R
import com.susanghan.android.databinding.DialogServiceErrorBinding

class ServiceErrorDialog:BaseBottomSheetDialogFragment<DialogServiceErrorBinding>() {
    override val bindingInflater = {layoutInflater:LayoutInflater, viewGroup:ViewGroup?, b:Boolean ->
        DialogServiceErrorBinding.inflate(layoutInflater, viewGroup, b)
    }

    override fun setEvent() {
        binding.llConfirm.setOnClickListener {
            dismiss()
            Log.e("#debug", "click confirm")
        }
    }
}