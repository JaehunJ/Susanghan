package com.susanghan.android.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignUpResultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpResultFragment : BaseFragment<FragmentSignUpResultBinding, SignUpResultViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_result
    override val viewModel: SignUpResultViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}