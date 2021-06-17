package com.amiir.boomino.di

import android.app.Application
import com.core.db.AppDatabase
import com.core.utils.Preference
import com.core.utils.PreferenceImpl
import com.core.utils.SettingManager
import com.core.utils.SettingManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    fun providePreference(app: Application): Preference {
        return PreferenceImpl(app)
    }

    @Provides
    fun provideSettingManager(preference: Preference): SettingManager {
        return SettingManagerImpl(preference)
    }
}
