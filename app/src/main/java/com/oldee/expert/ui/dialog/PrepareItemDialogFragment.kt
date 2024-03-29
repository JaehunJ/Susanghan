package com.oldee.expert.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.oldee.expert.data.PrepareItem
import com.oldee.expert.data.PrepareItemMappingList
import com.oldee.expert.data.PrepareItemMappingStringList
import com.oldee.expert.databinding.DialogPrepareItemAddBinding
import com.oldee.expert.databinding.LayoutDesignAddDialogPrepareItemBinding
import com.oldee.expert.ui.MainActivity


class PrepareItemDialogFragment(val onSuccess: (PrepareItem, String) -> Unit) : DialogFragment() {
    lateinit var binding: DialogPrepareItemAddBinding
    private var prepareItemBinding = mutableListOf<LayoutDesignAddDialogPrepareItemBinding>()

    private var name: String = ""
    private var code: PrepareItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPrepareItemAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.glPrepareItem.children.forEachIndexed { index, view ->
            prepareItemBinding.add(DataBindingUtil.bind(view)!!)
        }

        prepareItemBinding.forEachIndexed { index, view ->
            view.prepareItem = PrepareItemMappingList[index]
            view.btnCheck.visibility = View.GONE
            view.root.setOnClickListener { v ->
                prepareItemBinding.forEach { target ->
                    target.btnCheck.visibility = View.GONE
                }

                view.btnCheck.visibility = View.VISIBLE
                code = view.prepareItem
            }
            var type = view.prepareItem
            Glide.with(requireActivity()).load(PrepareItemMappingStringList[type?.value ?: "00"])
                .into(view.ivProduct)
        }

        binding.btnConfirm.setOnClickListener {
            name = binding.etName.editText?.text.toString()
            if (code == null || name.isEmpty()) {
                (requireActivity() as MainActivity).showToast("누락된 정보가 있습니다.")
            } else {
                onSuccess(code!!, name)
                dismiss()
            }
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}