package com.susanghan.android.ui.more.profile

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentProductBinding
import com.susanghan.android.databinding.FragmentProfileDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDetailFragment:BaseFragment<FragmentProfileDetailBinding, ProfileDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_profile_detail
    override val viewModel: ProfileDetailViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "프로필 관리"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}