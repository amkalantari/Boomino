package com.amiir.boomino.di

import com.amiir.boomino.ui.login.LoginViewModelFactory
import com.amiir.boomino.ui.main.MainViewModelFactory
import com.core.db.AppDatabase
import com.core.repository.AccountRepository
import dagger.Module
import dagger.Provides


@Module
class ViewModelFactory {

    @Provides
    fun provideLoginViewModelFactory(
        appDatabase: AppDatabase,
        accountRepository: AccountRepository
    ): LoginViewModelFactory {
        return LoginViewModelFactory(appDatabase.childDao(), accountRepository)
    }

    @Provides
    fun provideMainViewModelFactory(
        database: AppDatabase,
    ): MainViewModelFactory {
        return MainViewModelFactory(database.childDao())
    }

}