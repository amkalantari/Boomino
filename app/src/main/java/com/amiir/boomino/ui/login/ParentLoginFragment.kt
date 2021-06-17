package com.amiir.boomino.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentParentLoginBinding
import com.amiir.boomino.ui.main.MainActivity
import com.core.parent.ParentSharedFragment

class ParentLoginFragment : ParentSharedFragment<LoginViewModel, FragmentParentLoginBinding>() {

    override fun getResourceLayoutId(): Int = R.layout.fragment_parent_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getParentLoginResponse().observe(this, {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.parentButton.setOnDelayClickListener {
            viewModel.requestParentLogin(
                binding.userNameTextInput.getText(),
                binding.passWordTextInput.getText()
            )
        }

    }

    override fun hideProgress(tag: String) {
        super.hideProgress(tag)
        binding.loading = false
    }

    override fun showProgress(tag: String) {
        super.showProgress(tag)
        binding.loading = true
    }

    override fun showError(tag: String, error: String) {
        super.showError(tag, error)
        showMessage(tag)
    }

}