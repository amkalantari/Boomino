package com.amiir.boomino.ui.main.fragment

import android.os.Bundle
import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentCreateChildBinding
import com.amiir.boomino.ui.main.MainViewModel
import com.amiir.boomino.util.PackageHelper
import com.core.dto.ChildDto
import com.core.parent.ParentSharedFragment

class CreateChildFragment : ParentSharedFragment<MainViewModel, FragmentCreateChildBinding>() {

    private var selectedApp: List<String> = mutableListOf()

    override fun getResourceLayoutId(): Int = R.layout.fragment_create_child

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkboxList.setup {
            selectedApp = it
        }

        binding.checkboxList.setItems(PackageHelper.getPackages(requireContext(), mutableListOf()))

        binding.createChildButton.setOnDelayClickListener {
            if (binding.userNameTextInput.getText() != "") {
                viewModel.insertChild(
                    ChildDto(
                        userName = binding.userNameTextInput.getText(),
                        blockList = selectedApp
                    )
                )
                onBackPressed()
            }else{
                showMessage("Username should not empty")
            }
        }


    }
}