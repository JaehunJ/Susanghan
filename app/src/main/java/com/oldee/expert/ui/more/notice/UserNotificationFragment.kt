package com.oldee.expert.ui.more.notice

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentUserNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserNotificationFragment :
    BaseFragment<FragmentUserNotificationBinding, UserNotificationViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_user_notification
    override val viewModel: UserNotificationViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "알림"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}