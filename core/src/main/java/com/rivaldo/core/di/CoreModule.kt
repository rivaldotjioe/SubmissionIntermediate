package com.rivaldo.core.di

import android.content.Context
import android.util.Log
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.rivaldo.core.data.Constant
import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.data.local.LocalDataSource
import com.rivaldo.core.data.local.room.StoryDatabase
import com.rivaldo.core.data.remote.api.ApiService
import com.rivaldo.core.data.repository.FavoriteRepository
import com.rivaldo.core.data.repository.LoginRepository
import com.rivaldo.core.data.repository.RegisterRepository
import com.rivaldo.core.data.repository.StoriesRepository
import com.rivaldo.core.domain.repoInterface.IFavoriteRepository
import com.rivaldo.core.domain.repoInterface.ILoginRepository
import com.rivaldo.core.domain.repoInterface.IRegisterRepository
import com.rivaldo.core.domain.repoInterface.IStoriesRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStorePreferences.PREFERENCES_NAME)
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.DEFAULT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        Log.d("NetworkModule", "Retrofit Created EndPoint : ${Constant.DEFAULT_URL}")
        retrofit.create(ApiService::class.java)
    }
}

val localDataModule = module {
    single <DataStorePreferences> { DataStorePreferences.getInstance(androidContext()) }
    factory { get<StoryDatabase>().storyDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            StoryDatabase::class.java, "Story.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    single { com.rivaldo.core.data.remote.api.RemoteDataSource(get()) }
    single { LocalDataSource(get())}
    single<IRegisterRepository> { RegisterRepository(get()) }
    single<ILoginRepository> { LoginRepository(get()) }
    single<IStoriesRepository> { StoriesRepository(get(), get()) }
    single<IFavoriteRepository> { FavoriteRepository(get()) }
}