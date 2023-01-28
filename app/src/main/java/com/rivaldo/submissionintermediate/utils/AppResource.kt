package com.rivaldo.submissionintermediate.utils

import android.app.Application
import com.rivaldo.submissionintermediate.di.localDataModule
import com.rivaldo.submissionintermediate.di.networkModule
import com.rivaldo.submissionintermediate.di.repositoryModule
import com.rivaldo.submissionintermediate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppResource : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppResource)
            modules(
                listOf(
                    networkModule,
                    localDataModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}