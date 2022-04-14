package com.oldee.expert.custom

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("wonText")
fun setWonText(view: TextView, price:Int){
    val decFormat = DecimalFormat("###,###")
    val str = decFormat.format(price)
    view.text = String.format("%s원", str)
}