package com.amiir.boomino

import com.amiir.boomino.di.DaggerAppComponent
import com.core.base.BaseApp
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

/**
 * Created by aMiir on 9/31/21
 * Drunk, Fix Later
 */
open class App : BaseApp() {

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