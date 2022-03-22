package com.susanghan.android.ui.order

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.Period
import com.susanghan.android.databinding.FragmentOrderOldBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_old
    override val viewModel: OrderViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        val adapter = OrderListAdapter(navController!!){iv,s->
            viewModel.setImage(iv, s)
        }
        viewModel.orderList.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }

        binding.rvList.adapter = adapter
    }

    override fun initAfterBinding() {
        viewModel.requestOderList(0, 10, Period.MonthTotal.value)
        viewModel.requestUserProfile()
    }
}