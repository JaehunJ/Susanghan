package com.susanghan.android.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
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
        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position ?: 0

                val status = when(pos){
                    Period.Month3.value->Period.Month3
                    Period.Month6.value->Period.Month6
                    Period.Month12.value->Period.Month12
                    else->Period.MonthTotal
                }

                Log.e("#debug", "tabpositon ${status.value}")

                viewModel.requestOderList(0, 10, status.value)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
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
        viewModel.orderCount.observe(viewLifecycleOwner){
            it?.let{

            }
        }

//        binding.
        binding.rvList.adapter = adapter
    }

    override fun initAfterBinding() {
        viewModel.requestOrderCount()
        viewModel.requestOderList(0, 10, Period.MonthTotal.value)
        viewModel.requestUserProfile()
    }
}