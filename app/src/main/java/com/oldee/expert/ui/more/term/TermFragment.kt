package com.oldee.expert.ui.more.term

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentTermBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermFragment : BaseFragment<FragmentTermBinding, TermViewModel, NavArgs>(),
    TabLayout.OnTabSelectedListener {
    override val layoutId: Int = R.layout.fragment_term
    override val viewModel: TermViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private val urls = arrayListOf(
        BuildConfig.BASE_URL + "/api/v1/policy/service",
        BuildConfig.BASE_URL + "/api/v1/policy/privacy",
        ""
    )

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "이용정책"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.tlTop.addOnTabSelectedListener(this)

        binding.wbPage.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                activityFuncFunction.showProgress()

                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                activityFuncFunction.hideProgress()

                super.onPageFinished(view, url)
            }
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.wbPage.loadUrl(urls[0])
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            val url = it.position
            binding.wbPage.loadUrl(urls[url])
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}
