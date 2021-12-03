package com.susanghan.android.ui.more.account

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment:BaseFragment<FragmentAccountBinding, AccountViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_account
    override val viewModel: AccountViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "계정 관리"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.llLogout.setOnClickListener {
//            MaterialAlertDialogBuilder(requireContext())
//                .setTitle(resources.getString(R.string.account_dialog_start_title))
//                .setMessage(resources.getString(R.string.account_dialog_start_subtext))
//                .show()
        }

        binding.llStop.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog)
                .setTitle(resources.getString(R.string.account_dialog_start_title))
                .setMessage(resources.getString(R.string.account_dialog_start_subtext))
                .setNegativeButton(resources.getString(R.string.account_dialog_button_cancel)){dialog, which->

                }
                .setPositiveButton(resources.getString(R.string.account_dialog_button_start)){dialogInterface, i ->

                }
                .show()
        }

        binding.llWithdraw.setOnClickListener {
            navController?.navigate(R.id.action_accountFragment_to_withdrawFragment)
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}