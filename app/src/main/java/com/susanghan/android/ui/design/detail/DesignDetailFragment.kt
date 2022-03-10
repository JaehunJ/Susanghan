package com.susanghan.android.ui.design.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.DESIGN_FIRST
import com.susanghan.android.data.DESIGN_START
import com.susanghan.android.data.DESIGN_STOP
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
    private lateinit var smallAdapter:DesignItemImageAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "디자인 상세"
        reformId = navArgs.id
        adapter = DesignImageAdapter{iv, n->
            viewModel.setImage(iv, n)
        }
        binding.vpImage.adapter = adapter

        smallAdapter = DesignItemImageAdapter(requireContext())
        binding.rvItemSmall.adapter = smallAdapter

        binding.btnStop.setOnClickListener {
            viewModel.requestDesignDetailStateUpdate(reformId,DESIGN_STOP)
        }

        binding.btnStart.setOnClickListener {
            viewModel.requestDesignDetailStateUpdate(reformId,DESIGN_START)
        }

        binding.btnModify.setOnClickListener {
            val action = DesignDetailFragmentDirections.actionDesignDetailFragmentToDesignAddFragment(reformId)
        }

        binding.tvTitleInfo.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitleBa.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitlePrepare.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitleDay.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){
            viewModel.setImage(binding.ivBefore, it.beforeImageName?:"")
            viewModel.setImage(binding.ivAfter, it.afterImageName?:"")

            binding.detail = viewModel.data.value
            smallAdapter.submitList(it.items)

            when(it.status){
                0->{
                    binding.btnModify.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.VISIBLE
                    binding.btnStop.visibility = View.GONE
                    binding.btnBlank.visibility = View.GONE
                }
                1->{
                    binding.btnModify.visibility = View.GONE
                    binding.btnStart.visibility = View.GONE
                    binding.btnStop.visibility = View.VISIBLE
                    binding.btnBlank.visibility = View.VISIBLE
                }
                else->{
                    binding.btnModify.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                    binding.btnStop.visibility = View.GONE
                    binding.btnBlank.visibility = View.VISIBLE
                }
            }
        }

        viewModel.imageList.observe(viewLifecycleOwner){
            adapter.submitList(it)

            binding.indicator.setViewPager2(binding.vpImage)
            binding.indicator.refreshDots()
//            binding.indicator.notifySubtreeAccessibilityStateChanged()
        }
    }

    override fun initAfterBinding() {
        viewModel.requestDesignDetail(reformId)
    }
}