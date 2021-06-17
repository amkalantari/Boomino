package com.amiir.boomino.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentMainLoginBinding
import com.amiir.boomino.util.bindingAdapter.navigateSafe
import com.core.parent.ParentSharedFragment

class MainLoginFragment : ParentSharedFragment<LoginViewModel, FragmentMainLoginBinding>() {

    override fun getResourceLayoutId(): Int = R.layout.fragment_main_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.childButton.setOnDelayClickListener {
            findNavController().navigateSafe(MainLoginFragmentDirections.toChild())
        }
        binding.parentButton.setOnDelayClickListener {
            findNavController().navigateSafe(MainLoginFragmentDirections.toParent())
        }
    }

}