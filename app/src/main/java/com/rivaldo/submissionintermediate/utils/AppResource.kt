package com.rivaldo.submissionintermediate.utils

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.di.localDataModule
import com.rivaldo.core.di.networkModule
import com.rivaldo.core.di.repositoryModule
import com.rivaldo.submissionintermediate.di.interactorModule
import com.rivaldo.submissionintermediate.di.viewModelModule
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import com.rivaldo.submissionintermediate.ui.main.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppResource : Application(), Application.ActivityLifecycleCallbacks {


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
        val dataStorePreferences = get<DataStorePreferences>()
        runBlocking {
            checkIsLogin(dataStorePreferences)
        }

    }


    private suspend fun checkIsLogin(dataStorePreferences: DataStorePreferences) {
        val isLoggedIn = dataStorePreferences.getIsLoggedIn().first()
        if (isLoggedIn) {
            startActivity(Intent(applicationContext, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
        val dataStorePreferences = get<DataStorePreferences>()
        runBlocking {
            checkIsLogin(dataStorePreferences)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }
}