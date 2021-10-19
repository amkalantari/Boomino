package com.amiir.boomino.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.amiir.boomino.R
import com.amiir.boomino.databinding.ActivityMainBinding
import com.amiir.boomino.di.DaggerAppComponent
import com.amiir.boomino.ui.login.LoginActivity.Companion.USERNAME
import com.core.parent.ParentActivity
import javax.inject.Inject

class MainActivity : ParentActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setHomeButtonEnabled(true)

        if (intent.hasExtra(USERNAME)) {
            val username = intent.getStringExtra(USERNAME)!!
            viewModel.setUsername(username)
        }

    }

    override fun getResourceLayoutId(): Int = R.layout.activity_main

    override fun inject() {
        DaggerAppComponent.builder().app(application).build().inject(this)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory = factory


}