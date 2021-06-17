package com.amiir.boomino.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentAppListBinding
import com.amiir.boomino.ui.main.MainViewModel
import com.amiir.boomino.ui.main.PackageAdapter
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

        if (viewModel.getUsername() == "parent") {
            binding.createChildButton.visibility = View.VISIBLE
            adapterPackage.submitList(PackageHelper.getPackages(requireContext(), mutableListOf()))
        } else {
            binding.createChildButton.visibility = View.GONE
            viewModel.getBlockList(viewModel.getUsername()).observe(viewLifecycleOwner,{
                adapterPackage.submitList(PackageHelper.getPackages(requireContext(),it))
            })
        }


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