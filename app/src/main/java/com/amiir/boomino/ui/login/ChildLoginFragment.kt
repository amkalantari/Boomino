package com.amiir.boomino.ui.login

import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentChildLoginBinding
import com.core.parent.ParentSharedFragment

class ChildLoginFragment : ParentSharedFragment<LoginViewModel, FragmentChildLoginBinding>() {

    override fun getResourceLayoutId(): Int = R.layout.fragment_child_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

}