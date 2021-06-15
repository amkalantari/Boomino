package com.amiir.boomino

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.amiir.boomino.ui.MainActivity
import com.core.base.BaseApp
import com.core.db.AppDatabase
import com.core.utils.SettingManager
import com.amiir.boomino.di.DaggerAppComponent
import io.reactivex.Completable
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

/**
 * Created by aMiir on 9/31/21
 * Drunk, Fix Later
 */
open class App : BaseApp() {

    @Inject
    lateinit var tazminchiDb: AppDatabase

    @Inject
    lateinit var settingManager: SettingManager

    private fun handleUncaughtExceptionHandler(e: Throwable) {
        try {
            var exceprionMessage = "****************************************************\n"
            exceprionMessage += """
            exception ClassName    : ${e.javaClass.simpleName}
            
            """.trimIndent()
            if (e.stackTrace.size != 0) {
                exceprionMessage += """
                exception MethodName   : ${e.stackTrace[0].methodName}
                
                """.trimIndent()
                exceprionMessage += """
                exception LineNumber   : ${e.stackTrace[0].lineNumber}
                
                """.trimIndent()
            }
            exceprionMessage += """
            exception Message      : ${e.message}
            
            """.trimIndent()
            exceprionMessage += "****************************************************\n"
            Timber.e("Exception $exceprionMessage")
        } catch (ex: java.lang.Exception) {
            Timber.e("Exception ${ex.message}")
        }

    }


    override fun logoutAndRestart() {
        settingManager.setRegister(false)
        settingManager.setRefreshToken("")
        settingManager.setAccessToken("")
        addExecutorThreads(Completable.fromAction {
            tazminchiDb.clearAllTables()
        }, {
            Handler(Looper.getMainLooper()).postDelayed({
                val registerActivity = Intent(applicationContext, MainActivity::class.java)
                registerActivity.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                applicationContext.startActivity(registerActivity)
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(0)
            }, 1000)
        })
    }


    override fun onCreate() {
        super.onCreate()
        inject()

        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            println("exception check ${throwable?.message}")
            if (throwable != null)
                handleUncaughtExceptionHandler(throwable)
        }

        val mAndroidCrashHandler = Thread.getDefaultUncaughtExceptionHandler()

        val mUncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, exception ->
            try {
                handleUncaughtExceptionHandler(exception)
            } finally {
                mAndroidCrashHandler?.uncaughtException(thread, exception)
            }
        }

        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler)


    }

    fun inject() {
        DaggerAppComponent.builder().app(this).build().inject(this)
    }

}