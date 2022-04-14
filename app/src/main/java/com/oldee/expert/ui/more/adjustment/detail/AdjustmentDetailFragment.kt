package com.oldee.expert.ui.more.adjustment.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentAdjustmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdjustmentDetailFragment :
    BaseFragment<FragmentAdjustmentDetailBinding, AdjustmentDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_adjustment_detail
    override val viewModel: AdjustmentDetailViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "상세 내역 보기"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }


    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}