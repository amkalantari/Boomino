package com.amiir.boomino.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amiir.boomino.util.PackageHelper
import com.core.base.BaseViewModel
import com.core.dto.PackageDto
import com.core.utils.SettingManager

abstract class MainViewModel : BaseViewModel() {

//    abstract fun getApps():ArrayList<PackageDto>

}

class MainViewModelImpl(
    private var settingManager: SettingManager
) : MainViewModel() {
//    override fun getApps(): ArrayList<PackageDto> {
//        return PackageHelper.getPackages()
//    }

}

class MainViewModelFactory(
    private var settingManager: SettingManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModelImpl(settingManager) as T
    }
}