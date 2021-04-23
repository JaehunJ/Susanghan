package com.susanghan.android.ui.more.notice

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentUserNotificationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserNotificationFragment : BaseFragment<FragmentUserNotificationBinding, UserNotificationViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_user_notification
    override val viewModel: UserNotificationViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}