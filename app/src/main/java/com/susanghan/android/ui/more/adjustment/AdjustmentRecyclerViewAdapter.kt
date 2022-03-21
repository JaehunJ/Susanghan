package com.susanghan.android.ui.more.adjustment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdjustmentRecyclerViewAdapter :
    RecyclerView.Adapter<AdjustmentRecyclerViewAdapter.AdjustmentItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdjustmentItemViewHolder {
//        TODO("Not yet implemented")

        return AdjustmentItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AdjustmentItemViewHolder, position: Int) {
//        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return 0
    }

    class AdjustmentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}