package com.amiir.boomino.di

import android.app.Application
import com.core.db.AppDatabase
import com.core.utils.*
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
    fun provideSecurityHelper(): SecurityHelper {
        return SecurityHelperImpl()
    }

    @Provides
    fun providePreference(app: Application, securityHelper: SecurityHelper): Preference {
        return PreferenceImpl(app, securityHelper)
    }

    @Provides
    fun provideSettingManager(preference: Preference): SettingManager {
        return SettingManagerImpl(preference)
    }
}
