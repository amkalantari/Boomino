package com.amiir.boomino.ui

import com.amiir.boomino.R
import com.amiir.boomino.databinding.ActivityMainBinding
import com.core.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getResourceLayoutId(): Int = R.layout.activity_main

    override fun inject() {
        DaggerAppComponent.builder().app(application).build().inject(this)
    }

}