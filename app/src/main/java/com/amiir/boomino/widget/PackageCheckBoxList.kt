package com.amiir.boomino.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amiir.boomino.R
import com.amiir.boomino.databinding.ItemLayoutChechboxPackageBinding
import com.amiir.boomino.databinding.LayoutPackageCheckboxListBinding
import com.core.base.BaseHolder
import com.core.dto.PackageDto

class PackageCheckBoxList : FrameLayout {

    private lateinit var adapter: CheckBoxItemAdapter
    private var selectedItems = mutableListOf<String>()

    lateinit var binding: LayoutPackageCheckboxListBinding

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context)
    }

    private fun initialize(context: Context?) {

        val inflater = LayoutInflater.from(context)

        binding = DataBindingUtil
            .inflate(inflater, R.layout.layout_package_checkbox_list, this, true)

    }

    fun setup(
        itemClickCallBack: ((List<String>) -> Unit)
    ) {
        adapter = CheckBoxItemAdapter(itemClickCallBack)
        binding.filterList.adapter = adapter
    }

    fun setItems(
        items: List<PackageDto>
    ) {
        adapter.submitList(items)
    }

    fun setSelectedItems(selectedItems: List<String>) {
        this.selectedItems.clear()
        this.selectedItems.addAll(selectedItems)

        onChange()
    }

    private fun onChange() {
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }

    inner class CheckBoxItemAdapter(
        var itemClickCallBack: ((List<String>) -> Unit)?
    ) :
        ListAdapter<PackageDto, BaseHolder<PackageDto>>(object :
            DiffUtil.ItemCallback<PackageDto>() {

            override fun areItemsTheSame(
                oldItem: PackageDto,
                newItem: PackageDto
            ): Boolean = false

            override fun areContentsTheSame(
                oldItem: PackageDto,
                newItem: PackageDto
            ): Boolean = false

        }) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseHolder<PackageDto> {
            val inflater = LayoutInflater.from(parent.context)
            return ProductViewHolder(
                ItemLayoutChechboxPackageBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: BaseHolder<PackageDto>, position: Int) {
            holder.bind(getItem(position), position)
        }


        inner class ProductViewHolder(private val binding: ItemLayoutChechboxPackageBinding) :
            BaseHolder<PackageDto>(binding) {
            override fun bind(
                value: PackageDto,
                position: Int,
            ) {
                binding.item = value

                binding.filterLayoutMain.setOnClickListener {
                    binding.item?.let {
                        val key = it.appname
                        if (selectedItems.indexOf(key) == -1)
                            selectedItems.add(key)
                        else
                            selectedItems.remove(key)

                        notifyDataSetChanged()

                        itemClickCallBack?.invoke(selectedItems)
                    }
                }

                binding.filterCheckbox.isChecked = selectedItems.indexOf(value.appname) != -1
            }
        }
    }

}
