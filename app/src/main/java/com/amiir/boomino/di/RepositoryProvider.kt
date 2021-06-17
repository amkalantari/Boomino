package com.amiir.boomino.di

import com.core.api.AccountApi
import com.core.repository.AccountRepository
import com.core.repository.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class RepositoryProvider {

    @Provides
    fun provideAccountRepository(retrofit: Retrofit): AccountRepository {
        return AccountRepositoryImpl(retrofit.create(AccountApi::class.java))
    }

}