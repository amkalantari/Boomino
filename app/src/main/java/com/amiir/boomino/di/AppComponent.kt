package com.amiir.boomino.di

import android.app.Application
import com.amiir.boomino.App
import com.amiir.boomino.ui.login.LoginActivity
import com.amiir.boomino.ui.main.MainActivity
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
    fun inject(app: LoginActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }

}
