package com.amiir.boomino.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.base.BaseViewModel
import com.core.utils.SettingManager

abstract class MainViewModel : BaseViewModel() {
}

class MainViewModelImpl(
    private var settingManager: SettingManager
) : MainViewModel() {

}

class MainViewModelFactory(
    private var settingManager: SettingManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModelImpl(settingManager) as T
    }
}