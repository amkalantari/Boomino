package com.amiir.boomino.ui.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amiir.boomino.databinding.ItemLayoutChildBinding
import com.core.base.BaseHolder
import com.core.dto.ChildDto

class ChildAdapter(
    private val callBack: (ChildDto) -> Unit,
) :
    ListAdapter<ChildDto, BaseHolder<ChildDto>>(object : DiffUtil.ItemCallback<ChildDto>() {

        override fun areItemsTheSame(oldItem: ChildDto, newItem: ChildDto): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ChildDto, newItem: ChildDto): Boolean =
            oldItem == newItem

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ChildDto> {
        val inflater = LayoutInflater.from(parent.context)
        return ShopsViewHolder(ItemLayoutChildBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder<ChildDto>, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ShopsViewHolder(private val binding: ItemLayoutChildBinding) :
        BaseHolder<ChildDto>(binding) {
        override fun bind(
            value: ChildDto,
            position: Int,
        ) {
            binding.item = value
            binding.root.setOnClickListener {
                callBack(value)
            }
        }
    }

}