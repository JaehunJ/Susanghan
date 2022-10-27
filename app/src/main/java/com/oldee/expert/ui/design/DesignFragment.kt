package com.oldee.expert.ui.design

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.AppBarStateChangeListener
import com.oldee.expert.custom.OnScrollEndListener
import com.oldee.expert.data.Period
import com.oldee.expert.data.ReformStatus
import com.oldee.expert.databinding.FragmentDesignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : BaseFragment<FragmentDesignBinding, DesignViewModel, NavArgs>(),
    SwipeRefreshLayout.OnRefreshListener{
    override val layoutId: Int = R.layout.fragment_design
    override val viewModel: DesignViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    var enablePostScroll = false
    lateinit var scrollListener:AppBarStateChangeListener
    lateinit var adapter:DesignListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.llBlankItem.visibility = View.VISIBLE
        binding.rvList.visibility = View.GONE
        binding.swList.setOnRefreshListener(this)
        binding.rvList.addOnScrollListener(OnScrollEndListener {
            addItem()
        })


        setHeaderBehavior()
        adapter = DesignListAdapter(findNavController()) { v, u ->
            viewModel.setImage(v, u)
        }
        binding.rvList.adapter = adapter
        binding.btnDesignAdd.setOnClickListener {
            val action = DesignFragmentDirections.actionDesignFragmentToDesignAddFragment()
            navController?.navigate(action)
        }
        binding.btnDesignAdd1.setOnClickListener {
            val action = DesignFragmentDirections.actionDesignFragmentToDesignAddFragment()
            navController?.navigate(action)
        }

//        binding.topTab.addOnTabSelectedListener(this)
    }

    override fun initDataBinding() {
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("post_design")
            ?.observe(
                viewLifecycleOwner
            ) {
                it?.let {
                    if (it.isNotEmpty()) {
                        enablePostScroll = true
                        Log.e("#debug", "get post_design msg")
                    }
                }
            }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNullOrEmpty() && viewModel.page == 0) {
                    binding.llBlankItem.visibility = View.VISIBLE
                    binding.rvList.visibility = View.GONE
                    adapter.submitList(it)
                } else if (it.isEmpty() && viewModel.page != 0) {

                } else {
                    binding.llBlankItem.visibility = View.GONE
                    binding.rvList.visibility = View.VISIBLE
                    adapter.submitList(it) {
                        Log.e("#debug", "call back")
                        if (viewModel.page == 0) {
                            binding.rvList.post {
                                Log.e("#debug", " post call back")
                                binding.rvList.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
            }
        }

        viewModel.reformStatus.observe(viewLifecycleOwner){
            it?.let{
                viewModel.requestDesignList(0, 10, it)
            }
        }
    }

    override fun initAfterBinding() {
//        viewModel.requestDesignList(0, 10, ReformStatus.None.value)
    }

    override fun onRefresh() {
        viewModel.requestDesignList(viewModel.page, 10, viewModel.reformStatus.value?:ReformStatus.None)
        binding.swList.isRefreshing = false
    }

    fun addItem() {
        viewModel.requestDesignList(viewModel.page + 1, 10, viewModel.reformStatus.value?:ReformStatus.None)
    }

    private fun setHeaderBehavior(){
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
                R.id.btn_all -> {
                    viewModel.reformStatus.postValue(ReformStatus.None)
                }
                R.id.btn_start -> {
                    viewModel.reformStatus.postValue(ReformStatus.Start)
                }
                else -> {
                    viewModel.reformStatus.postValue(ReformStatus.Stop)
                }
            }
        }
    }
}