package com.amiir.boomino.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentAppListBinding
import com.amiir.boomino.util.PackageHelper
import com.amiir.boomino.util.bindingAdapter.navigateSafe
import com.core.parent.ParentSharedFragment

class AppListFragment : ParentSharedFragment<MainViewModel, FragmentAppListBinding>() {

    private val adapterPackage: PackageAdapter by lazy {
        PackageAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.adapter = adapterPackage
        adapterPackage.submitList(PackageHelper.getPackages(requireContext()))

        binding.createChildButton.setOnDelayClickListener {
            findNavController().navigateSafe(AppListFragmentDirections.toCreate())
        }


    }

    override fun getResourceLayoutId(): Int = R.layout.fragment_app_list

    override fun getSkeletonLayoutId(): View? {
        return null
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
}