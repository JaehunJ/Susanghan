package com.oldee.expert.ui.order

import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.OnScrollEndListener
import com.oldee.expert.databinding.FragmentOrderOldBinding
import com.oldee.expert.databinding.LayoutTabIconOrderSortBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>(),
    SwipeRefreshLayout.OnRefreshListener {
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

        topSort.forEachIndexed { idx, item ->
            item.root.setOnClickListener {
                selectItem(idx)
            }
        }
        binding.llEmpty.visibility = View.VISIBLE
        binding.llExist.visibility = View.GONE
        binding.swList.setOnRefreshListener(this)

        binding.rvList.addOnScrollListener(OnScrollEndListener { addItem() })

        binding.tvTop1.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.orderFragment)
        }
    }

    override fun initDataBinding() {
        val adapter = OrderListAdapter(navController!!) { iv, s ->
            viewModel.setImage(iv, s, Size(50, 50))
        }
        viewModel.orderList.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isEmpty() && viewModel.page == 0) { // 비엇음, refresh
                    binding.llEmpty.visibility = View.VISIBLE
                    binding.llExist.visibility = View.GONE
                    adapter.submitList(it.toMutableList())
                    binding.rvList.scrollToPosition(0)
                } else if (it.isEmpty() && viewModel.page != 0) {

                } else {
                    binding.llEmpty.visibility = View.GONE
                    binding.llExist.visibility = View.VISIBLE
                    adapter.submitList(it)
                }
            }
        }
        viewModel.orderCount.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvTopTabTotal.text = "${getString(R.string.order_top_tab_0)} ${it.totalCnt}"
                binding.tvTopTabNew.text = "${getString(R.string.order_top_tab_1)} ${it.newCnt}"
                binding.tvTopTabDoing.text =
                    "${getString(R.string.order_top_tab_2)} ${it.progressCnt}"
                binding.tvTopTabComplete.text =
                    "${getString(R.string.order_top_tab_3)} ${it.completeCnt}"
            }
        }

        binding.rvList.adapter = adapter
    }

    override fun initAfterBinding() {
        viewModel.requestOrderCount()
        viewModel.requestUserProfile()

        //첫 탭 선택
        if (viewModel.orderList.getItemCount() == 0)
            topSort[0].root.performClick()
    }

    private fun selectItem(selectedIdx: Int) {
        topSort.forEachIndexed { idx, item ->
            if (idx == selectedIdx) {
                item.ivIcon.visibility = View.VISIBLE
                viewModel.requestOderList(0, 10, (selectedIdx + 1) * 3)
            } else {
                item.ivIcon.visibility = View.GONE
            }
        }
    }

    override fun onRefresh() {
        viewModel.requestOrderCount()
        viewModel.requestOderList(0, 10, viewModel.period)
        binding.swList.isRefreshing = false
    }

    fun addItem() {
        viewModel.requestOderList(viewModel.page + 1, 10, viewModel.period, true)
    }
}