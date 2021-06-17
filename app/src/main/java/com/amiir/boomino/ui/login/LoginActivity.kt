package com.amiir.boomino.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.amiir.boomino.R
import com.amiir.boomino.databinding.ActivityLoginBinding
import com.amiir.boomino.di.DaggerAppComponent
import com.core.parent.ParentActivity
import javax.inject.Inject

class LoginActivity : ParentActivity<LoginViewModel, ActivityLoginBinding>() {

    @Inject
    lateinit var factory: LoginViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getResourceLayoutId(): Int = R.layout.activity_login

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory = factory

    override fun inject() {
        DaggerAppComponent.builder()
            .app(application)
            .build()
            .inject(this)
    }


}