package com.amiir.boomino.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentParentLoginBinding
import com.amiir.boomino.ui.login.LoginActivity.Companion.PARENT
import com.amiir.boomino.ui.login.LoginActivity.Companion.USERNAME
import com.amiir.boomino.ui.login.LoginViewModel
import com.amiir.boomino.ui.main.MainActivity
import com.amiir.boomino.util.KeyBoardHelper
import com.core.parent.ParentSharedFragment

class ParentLoginFragment : ParentSharedFragment<LoginViewModel, FragmentParentLoginBinding>() {

    override fun getResourceLayoutId(): Int = R.layout.fragment_parent_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getParentLoginResponse().observe(this, {
            if (!isFirstTime) {
                startActivity(
                    Intent(requireActivity(), MainActivity::class.java).putExtra(
                        USERNAME,
                        PARENT
                    )
                )
                isFirstTime = true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parentButton.setOnDelayClickListener {
            isFirstTime = false
            KeyBoardHelper(requireActivity()).closeKeyBoard()
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