package com.amiir.boomino.ui.login

import android.view.View
import com.amiir.boomino.R
import com.amiir.boomino.databinding.FragmentMainLoginBinding
import com.core.parent.ParentSharedFragment

class MainLoginFragment : ParentSharedFragment<LoginViewModel, FragmentMainLoginBinding>() {
    override fun getResourceLayoutId(): Int = R.layout.fragment_main_login

    override fun getSkeletonLayoutId(): View? = null

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

}