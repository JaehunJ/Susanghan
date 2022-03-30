package com.susanghan.android.ui.order

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.Period
import com.susanghan.android.databinding.FragmentOrderOldBinding
import com.susanghan.android.databinding.LayoutTabIconOrderSortBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_old
    override val viewModel: OrderViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private val topSort = mutableListOf<LayoutTabIconOrderSortBinding>()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn3m.tvTabName.text = "3개월"
        binding.btn6m.tvTabName.text = "6개월"
        binding.btn12m.tvTabName.text = "12개월"

        topSort.add(binding.btn3m)
        topSort.add(binding.btn6m)
        topSort.add(binding.btn12m)

        topSort.forEachIndexed{idx,item->
            item.root.setOnClickListener {
                selectItem(idx)
            }
        }
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
        viewModel.requestUserProfile()

        //첫 탭 선택
        topSort[0].root.performClick()
    }

    private fun selectItem(selectedIdx:Int){
        topSort.forEachIndexed {idx, item->
            if(idx == selectedIdx){
                item.ivIcon.visibility = View.VISIBLE
                viewModel.requestOderList(0, 10, (selectedIdx+1)*3)
            }else{
                item.ivIcon.visibility = View.GONE
            }
        }
    }
}