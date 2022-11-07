package com.oldee.expert.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.oldee.expert.R
import com.oldee.expert.databinding.DialogOrderCarryBinding
import com.oldee.expert.retrofit.response.DeliveryListResponse

class OrderCarryDialogFragment(
    val success: (DeliveryListResponse.DeliveryItem?, String) -> Unit,
    val tempMenu: List<DeliveryListResponse.DeliveryItem>
) : BaseBottomSheetDialogFragment<DialogOrderCarryBinding>(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {

    override val bindingInflater = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
        DialogOrderCarryBinding.inflate(layoutInflater, viewGroup, b)
    }

    var selectedItem: DeliveryListResponse.DeliveryItem? = null
    var number: String = ""

    override fun setEvent() {
        val stringArr = mutableListOf<String>()
        tempMenu.forEach {
            stringArr.add(it.courerName)
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.list_order_carry, stringArr)
        binding.acCarry.setAdapter(adapter)
        binding.acCarry.onItemSelectedListener = this
        binding.acCarry.onItemClickListener = this

        binding.btnOk.setOnClickListener {
            number = binding.etNum.text.toString()
            success.invoke(selectedItem, number)
            dismiss()
        }
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedItem = tempMenu[position]
        binding.acCarry.clearFocus()
    }



//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
////        binding.acCarry.()
////        val im =
////            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////        im.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
//        return true
//    }

//    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val im = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        im.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
//    }
}