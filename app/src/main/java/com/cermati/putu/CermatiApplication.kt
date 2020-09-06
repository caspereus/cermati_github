package com.cermati.putu

import android.app.Application
import com.cermati.putu.di.module.networkModule
import com.cermati.putu.di.module.repositoryModule
import com.cermati.putu.di.module.serviceModule
import com.cermati.putu.di.module.viewModelModule
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CermatiApplication : Application() {

    companion object {
        val disposeAble: CompositeDisposable by lazy {
            CompositeDisposable()
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@CermatiApplication)
            androidLogger(level = Level.DEBUG)

            modules(
                networkModule, repositoryModule, serviceModule, viewModelModule
            )
        }
    }
}