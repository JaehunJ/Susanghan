package com.oldee.expert.ui.order.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.expert.databinding.LayoutDesignItemBinding
import com.oldee.expert.databinding.LayoutOrderDetailItemImageBinding
import com.oldee.expert.ui.design.DesignListAdapter

class OrderDetailImageAdapter(
    val context: Context,
    val imageCallBack: (ImageView, String) -> Unit
) : RecyclerView.Adapter<OrderDetailImageAdapter.DetailImageViewHolder>() {

    var list = mutableListOf<String>()

    fun replaceItem(l:List<String>){
        list.clear()
        list = l.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.bind(list[position], imageCallBack)
    }

    override fun getItemCount() = list.size

    class DetailImageViewHolder(val binding: LayoutOrderDetailItemImageBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):DetailImageViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOrderDetailItemImageBinding.inflate(layoutInflater, parent, false)
                return DetailImageViewHolder(binding)
            }
        }

        fun bind(path:String, callback:(ImageView, String)->Unit){
            callback(binding.ivImage, path)
        }
    }


}