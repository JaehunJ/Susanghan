package com.oldee.expert.ui.order

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.AppBarStateChangeListener
import com.oldee.expert.custom.OnScrollEndListener
import com.oldee.expert.custom.dpToPx
import com.oldee.expert.data.Period
import com.oldee.expert.databinding.FragmentOrderOldBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>(),
    SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int = R.layout.fragment_order_old
    override val viewModel: OrderViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: OrderListAdapter

    lateinit var scrollListener: AppBarStateChangeListener

    override fun initView(savedInstanceState: Bundle?) {
        binding.llEmpty.visibility = View.VISIBLE
        binding.rvList.visibility = View.GONE
        binding.swList.setOnRefreshListener(this)
        binding.rvList.addOnScrollListener(OnScrollEndListener { addItem() })


        binding.acSort.setDropDownBackgroundDrawable(requireContext().getDrawable(R.drawable.bg_round_4dp_white))

        adapter = OrderListAdapter(navController!!) { iv, s ->
            viewModel.setImageRound(iv, s, dpToPx(requireContext(), 8f).toInt())
        }
        binding.rvList.adapter = adapter

        setHeaderBehavior()
    }

    override fun initDataBinding() {
        viewModel.orderList.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isEmpty() && viewModel.page == 0) { // 비엇음, refresh
                    binding.llEmpty.visibility = View.VISIBLE
                    binding.rvList.visibility = View.GONE
                    adapter.submitList(it.toMutableList())
                    binding.rvList.scrollToPosition(0)
                } else if (it.isEmpty() && viewModel.page != 0) {

                } else {
                    binding.llEmpty.visibility = View.GONE
                    binding.rvList.visibility = View.VISIBLE
                    adapter.submitList(it)
                }
            }
        }

        viewModel.period.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.requestOderList(0, 10, it)
            }
        }

        viewModel.orderCount.observe(viewLifecycleOwner) {
//            it?.let {
//                binding.tvTopTabTotal.text = "${getString(R.string.order_top_tab_0)} ${it.totalCnt}"
//                binding.tvTopTabNew.text = "${getString(R.string.order_top_tab_1)} ${it.newCnt}"
//                binding.tvTopTabDoing.text =
//                    "${getString(R.string.order_top_tab_2)} ${it.progressCnt}"
//                binding.tvTopTabComplete.text =
//                    "${getString(R.string.order_top_tab_3)} ${it.completeCnt}"
//            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestOrderCount()
        viewModel.requestUserProfile()

        val adapterSort = ArrayAdapter(
            requireContext(),
            R.layout.list_order_sort,
            listOf("전체보기", "신규", "진행중", "완료")
        )
        binding.acSort.setAdapter(adapterSort)
    }

    override fun onRefresh() {
        viewModel.requestOrderCount()
        viewModel.requestOderList(0, 10, viewModel.period.value ?: Period.Month3)
        binding.swList.isRefreshing = false
    }

    fun addItem() {
        viewModel.requestOderList(
            viewModel.page + 1,
            10,
            viewModel.period.value ?: Period.Month3,
            true
        )
    }

    fun setHeaderBehavior() {
        scrollListener = object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                super.onOffsetChanged(appBarLayout, verticalOffset)

                appBarLayout?.let {
                    val offsetAlpha =
                        1F - ((appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat())
                    binding.topTabSortBack.alpha = offsetAlpha
                }
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                when (state) {
                    State.EXPANDED -> {
                        binding.rgTab.children.forEach {
                            it.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.selector_order_sort_tab
                            )
                            (it as RadioButton).setTextColor(
                                ContextCompat.getColorStateList(
                                    requireContext(),
                                    R.color.selector_btn_gray_round
                                )
                            )
                        }
                    }
                    else -> {
                        binding.rgTab.children.forEach {
                            it.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.selector_order_sort_tab_col
                            )
                            (it as RadioButton).setTextColor(
                                ContextCompat.getColorStateList(
                                    requireContext(),
                                    R.color.selector_check_order_black
                                )
                            )
                        }
                    }
                }
            }
        }

        binding.appBar.addOnOffsetChangedListener(scrollListener)

        binding.rgTab.setOnCheckedChangeListener { group, checkedId ->
            if (scrollListener.currentState != AppBarStateChangeListener.State.EXPANDED)
                return@setOnCheckedChangeListener

            when (checkedId) {
                R.id.btn_3m -> {
                    viewModel.period.postValue(Period.Month3)
                }
                R.id.btn_6m -> {
                    viewModel.period.postValue(Period.Month6)
                }
                else -> {
                    viewModel.period.postValue(Period.Month12)
                }
            }
        }
    }
}