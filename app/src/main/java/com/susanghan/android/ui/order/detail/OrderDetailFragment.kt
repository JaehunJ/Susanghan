package com.susanghan.android.ui.order.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentOrderDetailBinding
import com.susanghan.android.ui.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderDetailViewModel, OrderDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_order_detail
    override val viewModel: OrderDetailViewModel by viewModels()
    override val navArgs: OrderDetailFragmentArgs by navArgs()

    lateinit var subAdapter:OrderDetailSubAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "주문 상세"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        subAdapter = OrderDetailSubAdapter()
        binding.rvSub.adapter = subAdapter
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){
            subAdapter.submitList(it.data)
        }
    }

    override fun initAfterBinding() {
        viewModel.requestOrderDetail(navArgs.id)
    }
}