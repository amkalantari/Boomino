package com.amiir.boomino.di

import com.amiir.boomino.ui.login.LoginViewModelFactory
import com.amiir.boomino.ui.main.MainViewModelFactory
import com.core.repository.AccountRepository
import com.core.utils.SettingManager
import dagger.Module
import dagger.Provides


@Module
class ViewModelFactory {

    @Provides
    fun provideLoginViewModelFactory(
        settingManager: SettingManager,
        accountRepository: AccountRepository
    ): LoginViewModelFactory {
        return LoginViewModelFactory(settingManager, accountRepository)
    }

    @Provides
    fun provideMainViewModelFactory(
        settingManager: SettingManager,
    ): MainViewModelFactory {
        return MainViewModelFactory(settingManager)
    }

}