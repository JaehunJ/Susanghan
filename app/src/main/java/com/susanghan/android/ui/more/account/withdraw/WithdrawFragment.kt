package com.susanghan.android.ui.more.account.withdraw

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentWithdrawBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WithdrawFragment : BaseFragment<FragmentWithdrawBinding, WithdrawViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_withdraw
    override val viewModel: WithdrawViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "탈퇴신청"
        binding.toolbar.ivClose.setOnClickListener {
            navController?.popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog)
                .setTitle(resources.getString(R.string.withdraw_dialog_title))
                .setMessage(resources.getString(R.string.withdraw_dialog_subtext))
                .setPositiveButton(resources.getString(R.string.withdraw_dialog_btn)) { dialogInterface, i ->
                    navController?.popBackStack()
                }
                .show()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}