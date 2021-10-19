package com.amiir.boomino.ui.appList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amiir.boomino.databinding.ItemLayoutPackageBinding
import com.core.base.BaseHolder
import com.core.dto.PackageDto

class PackageAdapter :
    ListAdapter<PackageDto, BaseHolder<PackageDto>>(object : DiffUtil.ItemCallback<PackageDto>() {

        override fun areItemsTheSame(oldItem: PackageDto, newItem: PackageDto): Boolean =
            false

        override fun areContentsTheSame(oldItem: PackageDto, newItem: PackageDto): Boolean =
            false

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<PackageDto> {
        val inflater = LayoutInflater.from(parent.context)
        return ShopsViewHolder(ItemLayoutPackageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder<PackageDto>, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ShopsViewHolder(private val binding: ItemLayoutPackageBinding) :
        BaseHolder<PackageDto>(binding) {
        override fun bind(
            value: PackageDto,
            position: Int,
        ) {
            binding.item = value

        }
    }

}