package com.oldee.expert.ui.order.detail

import android.os.Bundle
import android.util.Size
import android.view.View
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.getBoldText
import com.oldee.expert.data.OrderStatus
import com.oldee.expert.data.OrderType
import com.oldee.expert.databinding.FragmentOrderDetailBinding
import com.oldee.expert.ui.dialog.OrderCarryDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel, OrderDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_order_detail
    override val viewModel: OrderDetailViewModel by viewModels()
    override val navArgs: OrderDetailFragmentArgs by navArgs()

    lateinit var subAdapter: OrderDetailSubAdapter

    var mode = OrderType.R

    private var statusIcons = mutableListOf<CheckBox>()
    private var statusDots = mutableListOf<CheckBox>()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "주문 상세"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        subAdapter = OrderDetailSubAdapter { iv, s ->
            viewModel.setImage(iv, s, Size(150, 150))
        }
        subAdapter.onClick = {
            val dialog = com.oldee.imageviewer.ImageViewerDialog(listOf(it)){ iv, str->
                viewModel.setImage(iv, str)
            }
//            dialog.isCancelable = false

            dialog.show(requireActivity().supportFragmentManager, "")
        }
        binding.rvSub.adapter = subAdapter

        binding.btnCarry.setOnClickListener {
            OrderCarryDialogFragment(
                { item, num ->
                    viewModel.courierCode = item?.courerCode ?: ""
                    viewModel.invoiceNumber = num
                    viewModel.requestChangeStatus(OrderStatus.ShipmentComplete.value)
                },
                viewModel.deliveryList.value?.data ?: listOf()
            ).show(parentFragmentManager, "carry")
        }

        statusDots.add(binding.cbDot0)
        statusDots.add(binding.cbDot1)
        statusDots.add(binding.cbDot2)

        statusIcons.add(binding.cbIc0)
        statusIcons.add(binding.cbIc1)
        statusIcons.add(binding.cbIc2)
        statusIcons.add(binding.cbIc3)
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner) { res ->
            subAdapter.submitList(res.data)

            res?.let {
                if (it.data.isNotEmpty()) {
                    val di = it.data[0]

                    binding.res = it.data[0]

                    var status = di.orderStatusCode ?: 0

                    setStatusBarPosition(status)
                    setMode(di.classCode ?: "")
                    setButton(status)
                    setMainPanel(status)

                    binding.tvAddr.text = "${di.shippingAddress} ${di.shippingAddressDetail}"
                }
            }
        }

        viewModel.successStatusChange.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    viewModel.requestOrderDetail(navArgs.id)
                }
            }
        }

        viewModel.requestCarriedCompany()
    }

    override fun initAfterBinding() {
        viewModel.requestOrderDetail(navArgs.id)
    }

    private fun setMainPanel(status: Int) {
        when (status) {
            OrderStatus.Cancel.value -> {
                binding.tvTotal.text = "총 환불 금액"
                binding.llAddr.visibility = View.GONE
                binding.llCancel.visibility = View.VISIBLE
            }
            else -> {
                binding.tvTotal.text = "총 금액"
                binding.llAddr.visibility = View.VISIBLE
                binding.llCancel.visibility = View.GONE
            }
        }
    }

    private fun setButton(status: Int) {
        binding.btnCarry.visibility = View.GONE
        binding.btnComplete.visibility = View.GONE
        binding.btnStart.visibility = View.GONE
        when (status) {
            OrderStatus.Ready.value, OrderStatus.OrderComplete.value -> {
                binding.btnStart.visibility = View.VISIBLE
                binding.btnStart.setOnClickListener {
                    viewModel.requestChangeStatus(OrderStatus.Working.value)
                }
            }
            OrderStatus.Working.value -> {
                binding.btnComplete.visibility = View.VISIBLE
                binding.btnComplete.setOnClickListener {
                    viewModel.requestChangeStatus(OrderStatus.WorkComplete.value)
                }
            }
            OrderStatus.WorkComplete.value -> {
                binding.btnCarry.visibility = View.VISIBLE
            }
            OrderStatus.Cancel.value -> {

            }
            else -> {

            }
        }
    }

    private fun setStatusBarPosition(status: Int) {
        val maxNum = when(status){
            OrderStatus.Working.value->{
                1
            }
            OrderStatus.WorkComplete.value->{
                2
            }
            OrderStatus.ShipmentComplete.value->{
                3
            }
            else->{
                0
            }
        }

        if(maxNum != 0){
            statusIcons.forEachIndexed { index, checkBox ->
                checkBox.isChecked = index <= maxNum
            }

            statusDots.forEachIndexed { index, checkBox ->
                checkBox.isChecked = index <= maxNum - 1
            }
        }else{
            statusIcons[0].isChecked = true
        }

//        statusBar.tvOrder0.alpha = 0.5f
//        statusBar.tvOrder2.alpha = 0.5f
//        statusBar.tvOrder3.alpha = 0.5f
//        statusBar.tvOrder4.alpha = 0.5f
//
//        val target = when (status) {
//            OrderStatus.Working.value -> {
//                statusBar.tvOrder2.text = getBoldText(statusBar.tvOrder2.text.toString())
//                statusBar.tvOrder2.alpha = 1.0f
//                statusBar.tvOrder2.id
//            }
//            OrderStatus.WorkComplete.value -> {
//                statusBar.tvOrder2.text = getBoldText(statusBar.tvOrder3.text.toString())
//                statusBar.tvOrder3.alpha = 1.0f
//                statusBar.tvOrder3.id
//            }
//            OrderStatus.ShipmentComplete.value -> {
//                statusBar.tvProgress.visibility = View.INVISIBLE
//                statusBar.tvComplete.visibility = View.VISIBLE
//                statusBar.tvOrder4.id
//            }
//            OrderStatus.Cancel.value -> {
//                statusBar.tvRe.visibility = View.VISIBLE
//                statusBar.tvProgress.visibility = View.INVISIBLE
//                statusBar.tvOrder0.id
//            }
//            else -> {
//                statusBar.tvOrder0.text = getBoldText(statusBar.tvOrder0.text.toString())
//                statusBar.tvOrder0.alpha = 1.0f
//                statusBar.tvOrder0.id
//
//            }
//        }
//
//        val lm = statusBar.cursor.layoutParams as ConstraintLayout.LayoutParams
//        lm.startToStart = target
//        lm.endToEnd = target
//
//        statusBar.cursor.layoutParams = lm
    }

    private fun setMode(mode: String) {
        this.mode = when (mode) {
            OrderType.R.type -> {
                OrderType.R
            }
            else -> {
                OrderType.A
            }
        }
    }
}