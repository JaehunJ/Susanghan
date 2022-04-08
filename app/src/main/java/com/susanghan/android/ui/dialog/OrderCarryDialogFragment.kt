package com.susanghan.android.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.susanghan.android.R
import com.susanghan.android.databinding.DialogOrderCarryBinding
import com.susanghan.android.retrofit.response.DeliveryListResponse

class OrderCarryDialogFragment(
    val success: (DeliveryListResponse.DeliveryItem?, String) -> Unit,
    val tempMenu: List<DeliveryListResponse.DeliveryItem>
) : DialogFragment(), AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    lateinit var binding: DialogOrderCarryBinding

    var selectedItem: DeliveryListResponse.DeliveryItem? = null
    var number: String = ""

//    val tempMenu = listOf("menu1", "menu2", "menu3")

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

        val stringArr = mutableListOf<String>()
        tempMenu.forEach {
            stringArr.add(it.courerName)
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.list_order_carry, stringArr)
        binding.acCarry.setAdapter(adapter)
        binding.acCarry.onItemSelectedListener = this
        binding.acCarry.onItemClickListener = this
//        binding.acCarry.setOnTouchListener(this)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            number = binding.etNum.text.toString()
            success.invoke(selectedItem, number)
            dismiss()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        selectedItem = tempMenu[position]
        Log.e("#debug", "onItemSelected")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("#debug", "onItemClick")
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