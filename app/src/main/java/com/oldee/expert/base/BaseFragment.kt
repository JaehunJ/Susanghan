package com.oldee.expert.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.oldee.expert.R
import com.oldee.expert.ui.CommonActivityFuncImpl

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel, NA : NavArgs> : Fragment() {
    var navController: NavController? = null

    private var _binding: T? = null

    val binding get() = _binding!!

    abstract val layoutId: Int

    abstract val viewModel: VM
    abstract val navArgs: NavArgs

    lateinit var activityFuncFunction: CommonActivityFuncImpl

    /**
     * layout infalte후 호출
     *
     * @param savedInstanceState
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 데이터 바인딩 처리
     *
     */
    abstract fun initDataBinding()

    /**
     * 데이터바인딩 후 할일(리스너 등록등)
     *
     */
    abstract fun initAfterBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            navController = findNavController()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        activityFuncFunction = activity as CommonActivityFuncImpl
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Log.e("#debug", "isLoading")
            if (it)
                activityFuncFunction.showProgress()
            else
                activityFuncFunction.hideProgress()
        }

        initView(savedInstanceState)
        initDataBinding()
        initAfterBinding()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewCreated()
    }

    open fun onViewCreated() {
        viewModel.hasError.observe(viewLifecycleOwner, getObserver(viewLifecycleOwner){
            it?.let {
                if(it) findNavController().navigate(R.id.action_global_networkErrorFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <T> getObserver(owner: LifecycleOwner, action:(T)->Unit)
            = Observer<T> { if(owner.lifecycle.currentState == Lifecycle.State.RESUMED) action.invoke(it)}
}