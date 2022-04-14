package com.oldee.expert.ui.more.profile

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentProfileDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDetailFragment :
    BaseFragment<FragmentProfileDetailBinding, ProfileDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_profile_detail
    override val viewModel: ProfileDetailViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "프로필 관리"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){
            if(it != null){
                binding.data = it.data
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestUserProfile()
    }
}