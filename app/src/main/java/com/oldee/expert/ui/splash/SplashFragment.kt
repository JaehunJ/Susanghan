package com.oldee.expert.ui.splash

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.checkPermission
import com.oldee.expert.data.AppStatus
import com.oldee.expert.databinding.FragmentSplashBinding
import com.oldee.expert.ui.dialog.ForceUpdateDialog
import com.oldee.expert.ui.dialog.PermissionDialog
import com.oldee.expert.ui.dialog.ServiceCheckDialog
import com.oldee.expert.ui.dialog.UpdateDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private val permissions =
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { per -> per.value == true }) {
                loadNext()
            }else{
                requireActivity().finish()
            }
        }


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner) { res ->
            res?.let {
                if(it.versionCode != BuildConfig.VERSION_NAME){
                    when (it.appStatus) {
                        AppStatus.Update.value -> {
                            showUpdateDialog()
                        }
                        AppStatus.ForceUpdate.value -> {
                            showForceUpdateDialog()
                        }
                        AppStatus.Check.value -> {
                            showServiceCheckDialog{
                                activity?.finish()
                            }
                        }
                        else -> {
                            showPermissionInfoDialog()
                        }
                    }
                }else{
                    showPermissionInfoDialog()
                }
            }

            if(res == null){
                showPermissionInfoDialog()
            }
        }
    }

    override fun initAfterBinding() {
        viewModel.requestVersionInfo()

//        activity?.let {
//            val dialog = ServiceErrorDialog {
////                showServiceCheckDialog()
//                showPermissionInfoDialog()
////                val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
////                navController?.navigate(action)
//            }
//
//            dialog.isCancelable = false
//            dialog.show(it.supportFragmentManager, "error")

//            showPermissionInfoDialog()
//        }
//        activityFunction?.showBottomNavi()
//        val action = SplashFragmentDirections.actionGlobalCsFragment()
//        navController?.navigate(action)
    }

    fun showServiceCheckDialog(onNext: () -> Unit) {
        val dialog = ServiceCheckDialog {
            onNext.invoke()
//            showPermissionInfoDialog()
        }

        dialog.isCancelable = false
        activity?.let {
            dialog.show(it.supportFragmentManager, "check")
        }
    }

    fun showPermissionInfoDialog() {
        val check = checkPermission(requireContext(), *permissions)

        if (!check) {
            val dialog = PermissionDialog {
                requestPermissionResult.launch(permissions)
            }
            dialog.isCancelable = false

            activity?.let {
                dialog.show(it.supportFragmentManager, "permission")
            }
        } else {
            loadNext()
        }
    }

    fun showForceUpdateDialog() {
        val dialog = ForceUpdateDialog {
            //TODO: call store
//            onNext()
//            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
//            navController?.navigate(action)
        }
        dialog.isCancelable = false

        activity?.let {
            dialog.show(it.supportFragmentManager, "force_update")
        }
    }

    fun showUpdateDialog() {
        val dialog = UpdateDialog({
            showPermissionInfoDialog()
        }) {
            //TODO: call store
//            showForceUpdateDialog()
        }
        dialog.isCancelable = false

        activity?.let {
            dialog.show(it.supportFragmentManager, "update")
        }
    }

    fun loadNext() {
        val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
        navController?.navigate(action)
    }
}