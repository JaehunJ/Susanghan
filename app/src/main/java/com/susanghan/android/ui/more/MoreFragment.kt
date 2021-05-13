package com.susanghan.android.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentMoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreFragment : BaseFragment<FragmentMoreBinding, MoreViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_more
    override val viewModel: MoreViewModel by viewModel()
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

        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}