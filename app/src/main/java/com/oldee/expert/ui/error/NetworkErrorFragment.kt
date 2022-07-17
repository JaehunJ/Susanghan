package com.oldee.expert.ui.error

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentNetworkErrorBinding
import com.oldee.expert.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkErrorFragment :
    BaseFragment<FragmentNetworkErrorBinding, NetworkErrorViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_network_error
    override val viewModel: NetworkErrorViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnHome.setOnClickListener {
            activity?.let{
                it.finishAffinity()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}