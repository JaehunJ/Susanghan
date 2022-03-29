package com.susanghan.android.ui.order.detail

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.OrderStatus
import com.susanghan.android.data.OrderType
import com.susanghan.android.databinding.FragmentOrderDetailBinding
import com.susanghan.android.ui.dialog.OrderCarryDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel, OrderDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_order_detail
    override val viewModel: OrderDetailViewModel by viewModels()
    override val navArgs: OrderDetailFragmentArgs by navArgs()

    lateinit var subAdapter: OrderDetailSubAdapter

    var mode = OrderType.R

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "주문 상세"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        subAdapter = OrderDetailSubAdapter { iv, s ->
            viewModel.setImage(iv, s)
        }
        binding.rvSub.adapter = subAdapter

        binding.btnCarry.setOnClickListener {
            OrderCarryDialogFragment {

            }.show(parentFragmentManager, "carry")
        }
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner) {
            subAdapter.submitList(it.data)

            it?.let {
                if (it.data.isNotEmpty()) {
                    val d = it.data[0]

                    binding.res = it.data[0]

                    setStatusBarPosition(d.orderStatusCode ?: 0)
                    setMode(d.classCode ?: "")
                    setButton(d.orderStatusCode ?: 0)
                    setMainPanel(d.orderStatusCode ?: 0)
                }
            }
        }
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
            OrderStatus.Ready.value, OrderStatus.Carry.value -> {
                binding.btnStart.visibility = View.VISIBLE
            }
            OrderStatus.Doing.value -> {
                binding.btnComplete.visibility = View.VISIBLE
            }
            OrderStatus.Cancel.value -> {

            }
            else -> {
                binding.btnCarry.visibility = View.VISIBLE
            }
        }
    }

    private fun setStatusBarPosition(status: Int) {
        val statusBar = binding.llStatusBar

        val target = when (status) {
            OrderStatus.Doing.value -> {
                statusBar.tvOrder2.id
            }
            OrderStatus.Complete.value -> {
                statusBar.tvOrder3.id
            }
            OrderStatus.CarriedComplete.value -> {
                statusBar.tvOrder4.id
            }
            OrderStatus.Cancel.value -> {
                statusBar.tvRe.visibility = View.VISIBLE
                statusBar.tvProgress.visibility = View.INVISIBLE
                statusBar.tvOrder0.id
            }
            else -> {
                statusBar.tvOrder0.id
            }
        }

        val lm = statusBar.cursor.layoutParams as ConstraintLayout.LayoutParams
        lm.startToStart = target
        lm.endToEnd = target

        statusBar.cursor.layoutParams = lm
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