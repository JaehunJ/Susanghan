package com.oldee.expert.custom

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class OnScrollEndListener(val callBack: () -> Unit) :
    RecyclerView.OnScrollListener() {
//
//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
////        val lm = recyclerView.layoutManager as T
////
////        val totalItem = lm.itemCount
////        val lastVisible = lm.findLastCompletelyVisibleItemPosition()
////
////        if (lastVisible >= totalItem - 1) {
////            Log.e("#http", "last item")
////            callBack.invoke()
////        }
//    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        //top 스크롤 리프래시 방지
        if(!recyclerView.canScrollVertically(-1) && !recyclerView.canScrollVertically(1))
            return

        //bottom 스크롤 리프래시
        if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
            Log.e("#debug", "refresh")
            callBack.invoke()
        }
    }
}