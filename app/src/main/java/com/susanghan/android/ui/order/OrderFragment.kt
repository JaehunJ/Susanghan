package com.susanghan.android.ui.order

import android.os.Bundle
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentOrderOldBinding
import com.susanghan.android.retrofit.response.OrderListResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_old
    override val viewModel: OrderViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        val adapter = OrderListAdapter()
        viewModel.orderList.observe(viewLifecycleOwner, {list->
            list?.let{
                adapter.submitList(it)
            }
        })

        binding.rvList.adapter = adapter
    }

    override fun initAfterBinding() {
        viewModel.requestOrderList()
    }
}