package com.susanghan.android.ui.order.detail

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.OrderStatus
import com.susanghan.android.data.OrderType
import com.susanghan.android.databinding.FragmentOrderDetailBinding
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

        subAdapter = OrderDetailSubAdapter{iv, s->
            viewModel.setImage(iv, s)
        }
        binding.rvSub.adapter = subAdapter
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner) {
            subAdapter.submitList(it.data)

            it?.let {
                if (it.data.isNotEmpty()) {
                    val d = it.data[0]

                    binding.res = it.data[0]

                    setStatusBarPosition(d.orderStatusCode?:0)
                    setMode(d.classCode?:"")
                }
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestOrderDetail(navArgs.id)
    }

    fun setStatusBarPosition(status: Int) {
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
            else -> {
                statusBar.tvOrder0.id
            }
        }

        val lm = statusBar.cursor.layoutParams as ConstraintLayout.LayoutParams
        lm.startToStart = target
        lm.endToEnd = target

        statusBar.cursor.layoutParams = lm
    }

    fun setMode(mode: String) {
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