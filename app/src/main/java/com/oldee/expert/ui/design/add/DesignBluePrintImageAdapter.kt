package com.oldee.expert.ui.design.add

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oldee.expert.databinding.LayoutDesignAddImageBinding

class DesignBluePrintImageAdapter(
    val addCallback: () -> Unit,
    val deleteCallback: (Int) -> Unit,
    val imageDrawCallback: (view: ImageView, data: DesignAddViewModel.ImageData) -> Unit
) :
    ListAdapter<DesignBluePrintImageAdapter.AdapterImageData, DesignBluePrintImageAdapter.BluePrintViewHolder>(
        BluePrintDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluePrintViewHolder {
        return BluePrintViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BluePrintViewHolder, position: Int) {
        holder.bind(
            position == (this.itemCount - 1),
            getItem(position),
            { addCallback() },
            { deleteCallback(position) },
            imageDrawCallback
        )
    }

    class BluePrintViewHolder(val binding: LayoutDesignAddImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): BluePrintViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val b = LayoutDesignAddImageBinding.inflate(layoutInflater, parent, false)

                return BluePrintViewHolder(b)
            }
        }

        fun bind(
            isAdd: Boolean,
            data: AdapterImageData,
            addCallback: () -> Unit,
            deleteCallback: () -> Unit,
            imageDrawCallback: (view: ImageView, data: DesignAddViewModel.ImageData) -> Unit
        ) {
            if (isAdd) {
                binding.clExist.visibility = View.GONE
                binding.clAdd.visibility = View.VISIBLE
                binding.clAdd.setOnClickListener {
                    addCallback()
                }
                return
            }

            binding.clExist.visibility = View.VISIBLE
            binding.clAdd.visibility = View.GONE

            if (data.str.contains(":/")) {
                imageDrawCallback.invoke(
                    binding.ivProduct,
                    DesignAddViewModel.ImageData(IMAGE_URI, null, Uri.parse(data.str))
                )
            } else {
                imageDrawCallback.invoke(
                    binding.ivProduct, DesignAddViewModel.ImageData(
                        IMAGE_SERVER, data.str, null
                    )
                )
            }

            binding.btnDelete.setOnClickListener {
                deleteCallback()
            }
        }
    }

    data class AdapterImageData(val str: String)
    class BluePrintDiffUtil : DiffUtil.ItemCallback<AdapterImageData>() {
        override fun areItemsTheSame(
            oldItem: AdapterImageData,
            newItem: AdapterImageData
        ): Boolean {
            return oldItem.str == newItem.str
        }

        override fun areContentsTheSame(
            oldItem: AdapterImageData,
            newItem: AdapterImageData
        ): Boolean {
            return oldItem == newItem
        }
    }
}