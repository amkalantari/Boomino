package com.amiir.boomino.ui.main

import androidx.lifecycle.ViewModelProvider
import com.amiir.boomino.R
import com.amiir.boomino.databinding.ActivityMainBinding
import com.amiir.boomino.di.DaggerAppComponent
import com.core.parent.ParentActivity
import javax.inject.Inject

class MainActivity : ParentActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun getResourceLayoutId(): Int = R.layout.activity_main

    override fun inject() {
        DaggerAppComponent.builder().app(application).build().inject(this)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getFactory(): ViewModelProvider.Factory = factory

}