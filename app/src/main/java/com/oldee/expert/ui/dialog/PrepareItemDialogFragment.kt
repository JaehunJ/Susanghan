package com.oldee.expert.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.oldee.expert.data.PrepareItem
import com.oldee.expert.data.PrepareItemMappingList
import com.oldee.expert.data.PrepareItemMappingStringList
import com.oldee.expert.databinding.DialogPrepareItemAddBinding
import com.oldee.expert.databinding.LayoutDesignAddDialogPrepareItemBinding
import com.oldee.expert.ui.MainActivity


class PrepareItemDialogFragment(val onSuccess: (PrepareItem, String) -> Unit) :
    BaseBottomSheetDialogFragment<DialogPrepareItemAddBinding>() {

    override val bindingInflater =
        { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            DialogPrepareItemAddBinding.inflate(layoutInflater, viewGroup, b)
        }

    private var prepareItemBinding = mutableListOf<LayoutDesignAddDialogPrepareItemBinding>()

    private var name: String = ""
    private var code: PrepareItem? = null


    override fun setEvent() {
        binding.glPrepareItem.children.forEachIndexed { index, view ->
            prepareItemBinding.add(DataBindingUtil.bind(view)!!)
        }

        prepareItemBinding.forEachIndexed { index, view ->
            view.prepareItem = PrepareItemMappingList[index]
            view.btnCheck.visibility = View.GONE
            view.root.setOnClickListener { v ->
                prepareItemBinding.forEach { target ->
                    target.btnCheck.visibility = View.GONE
                    target.ivProduct.alpha = 0.2f
                }

                view.btnCheck.visibility = View.VISIBLE
                code = view.prepareItem
                view.ivProduct.alpha = 1.0f
            }
            var type = view.prepareItem
            Glide.with(requireActivity()).load(PrepareItemMappingStringList[type?.value ?: "00"])
                .into(view.ivProduct)
        }

        binding.btnConfirm.setOnClickListener {
            name = binding.etName.text.toString()
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