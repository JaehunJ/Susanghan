package com.susanghan.android.ui.design.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignDetailFragment :
    BaseFragment<FragmentDesignDetailBinding, DesignDetailViewModel, DesignDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_design_detail
    override val viewModel: DesignDetailViewModel by viewModels()
    override val navArgs: DesignDetailFragmentArgs by navArgs()

    private var reformId = 0
    private lateinit var adapter: DesignImageAdapter

    override fun initView(savedInstanceState: Bundle?) {
        reformId = navArgs.id
        adapter = DesignImageAdapter{iv, n->
            viewModel.setImage(iv, n)
        }
        binding.vpImage.adapter = adapter
        binding.vpImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager2(binding.vpImage)
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){

        }

        viewModel.imageList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.requestDesignDetail(reformId)
    }

}