package com.amiir.boomino.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentChildLoginBinding
import com.amiir.boomino.ui.login.LoginActivity.Companion.USERNAME
import com.amiir.boomino.ui.login.LoginViewModel
import com.amiir.boomino.ui.login.adapter.ChildAdapter
import com.amiir.boomino.ui.main.MainActivity
import com.core.parent.ParentSharedFragment

class ChildLoginFragment : ParentSharedFragment<LoginViewModel, FragmentChildLoginBinding>() {

    private val adapter: ChildAdapter by lazy {
        ChildAdapter {
            startActivity(
                Intent(requireActivity(), MainActivity::class.java)
                    .putExtra(USERNAME, it.userName)
            )
        }
    }

    override fun getResourceLayoutId(): Int = R.layout.fragment_child_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = adapter

        val list = viewModel.getChildList()
        if (list.isNotEmpty()) {
            adapter.submitList(viewModel.getChildList())
        } else {
            binding.list.visibility = View.GONE
            binding.emptyLayout.visibility = View.VISIBLE
        }
    }

}