package com.amiir.boomino.di

import android.app.Application
import com.amiir.boomino.ui.MainActivity
import com.amiir.boomino.App
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            DatabaseModule::class,
            NetworkModule::class,
            RepositoryProvider::class,
            ViewModelFactory::class
        ]
)
interface AppComponent {

    fun inject(app: App)

    //Activity
    fun inject(app: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }

}
