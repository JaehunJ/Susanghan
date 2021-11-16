package com.susanghan.android.ui.order

import android.widget.TextView
import com.susanghan.android.retrofit.response.OrderListResponse

fun TextView.setOrderTitle(item:OrderListResponse.OrderData){
    text = item.itemCategory
}