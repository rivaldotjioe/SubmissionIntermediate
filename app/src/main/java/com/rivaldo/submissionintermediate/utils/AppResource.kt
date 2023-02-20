package com.rivaldo.submissionintermediate.utils

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.rivaldo.core.di.localDataModule
import com.rivaldo.core.di.networkModule
import com.rivaldo.core.di.repositoryModule
import com.rivaldo.submissionintermediate.di.interactorModule
import com.rivaldo.submissionintermediate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppResource : Application() {
    override fun onCreate() {
        super.onCreate()
        Glide.init(this, GlideBuilder())
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AppResource)
            modules(
                listOf(
                    networkModule,
                    localDataModule,
                    repositoryModule,
                    interactorModule,
                    viewModelModule,
                )
            )
        }
    }
}