package com.oldee.expert.ui.more

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding, MoreViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_more
    override val viewModel: MoreViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivNoti.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToUserNotificationFragment())
        }

        binding.llProfile.setOnClickListener {
            val action = MoreFragmentDirections.actionMoreFragmentToProfileDetailFragment()
            navController?.navigate(action)
        }

        binding.llMyWallet.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToAdjustmentFragment())
        }

        binding.llMyAccounts.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToAccountFragment())
        }

        binding.llCommonNotice.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToNotiFragment())
        }

        binding.llCommonQna.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToFaqFragment())
        }

        binding.llCommonTerm.setOnClickListener {
            navController?.navigate(MoreFragmentDirections.actionMoreFragmentToTermFragment())
        }

        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.profile.observe(viewLifecycleOwner) {
            binding.user = it
            it?.let{d->
                if(!d.profileImg.isNullOrEmpty()){
                    viewModel.setImageCircle(binding.ivProfile, d.profileImg)
                }
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestProfile()
        viewModel.getVersionInfo(requireContext())
    }
}