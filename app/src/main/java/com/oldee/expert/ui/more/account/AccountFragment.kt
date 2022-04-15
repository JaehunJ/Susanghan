package com.oldee.expert.ui.more.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.data.UserStatus
import com.oldee.expert.databinding.FragmentAccountBinding
import com.oldee.expert.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_account
    override val viewModel: AccountViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "계정 관리"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.llLogout.setOnClickListener {
            activity?.let{
                viewModel.logout()
                it.finishAffinity()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
//                System.exit(0)
            }
        }

        binding.llStart.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog)
                .setTitle(resources.getString(R.string.account_dialog_start_title))
                .setMessage(resources.getString(R.string.account_dialog_start_subtext))
                .setNegativeButton(resources.getString(R.string.account_dialog_button_cancel)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.account_dialog_button_start)) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    viewModel.requestChangeUserStatus(UserStatus.Start.value, "")
                }
                .show()
        }

        binding.llStop.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog)
                .setTitle(resources.getString(R.string.account_dialog_stop_title))
                .setMessage(resources.getString(R.string.account_dialog_stop_subtext))
                .setNegativeButton(resources.getString(R.string.account_dialog_button_cancel)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.account_dialog_button_stop)) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    viewModel.requestChangeUserStatus(UserStatus.Stop.value, "")
                }
                .show()
        }

        binding.llWithdraw.setOnClickListener {
            navController?.navigate(R.id.action_accountFragment_to_withdrawFragment)
        }

        binding.llStart.visibility = View.GONE
        binding.llStop.visibility = View.GONE
        binding.tvWithdrawSub.visibility = View.GONE
        binding.llStart.isClickable = true
        binding.llStop.isClickable = true
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let { d ->
                when(d.userStatus){
                    UserStatus.Stop.value->{
                        binding.llStart.visibility = View.VISIBLE
                        binding.llStop.visibility = View.GONE
                    }
                    UserStatus.Withdraw.value->{
                        binding.tvWithdrawSub.visibility = View.VISIBLE
                        binding.llStart.isClickable = false
                        binding.llStop.isClickable = false
                    }
                    else->{
                        binding.llStop.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestUserProfile()
    }
}