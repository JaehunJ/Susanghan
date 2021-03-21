package com.susanghan.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import java.lang.Exception

abstract class BaseFragment<T : ViewDataBinding, VM:BaseViewModel>: Fragment() {
    var navController: NavController? = null
    lateinit var binding:T
    abstract val layoutId:Int

    abstract val viewModel:VM
    abstract val navArgs:NavArgs

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
        try{
            navController = findNavController()
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this

        initView(savedInstanceState)
        initDataBinding()
        initAfterBinding()

        return binding.root
    }
}