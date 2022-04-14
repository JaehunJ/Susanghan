package com.oldee.expert.ui

interface CommonActivityFuncImpl {
    fun showProgress()
    fun hideProgress()
    fun showBottomNavi()
    fun hideBottomNavi()
    fun showToast(msg: String)
    fun showSnackBar(msg:String)
}