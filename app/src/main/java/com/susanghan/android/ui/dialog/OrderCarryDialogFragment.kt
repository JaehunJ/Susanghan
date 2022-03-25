package com.susanghan.android.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.susanghan.android.R
import com.susanghan.android.databinding.DialogOrderCarryBinding

class OrderCarryDialogFragment(val success: () -> Unit) : DialogFragment() {
    lateinit var binding: DialogOrderCarryBinding

    val tempMenu = listOf("menu1", "menu2", "menu3")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogOrderCarryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_order_carry, tempMenu)
        binding.acCarry.setAdapter(adapter)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            success.invoke()
            dismiss()
        }
    }
}