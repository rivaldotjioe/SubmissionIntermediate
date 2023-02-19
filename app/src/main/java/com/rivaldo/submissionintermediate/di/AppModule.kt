package com.rivaldo.submissionintermediate.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rivaldo.submissionintermediate.data.Constant
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.data.remote.api.ApiService
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.repository.LoginRepository
import com.rivaldo.submissionintermediate.data.remote.repository.RegisterRepository
import com.rivaldo.submissionintermediate.data.remote.repository.StoriesRepository
import com.rivaldo.submissionintermediate.domain.interactor.*
import com.rivaldo.submissionintermediate.domain.repoInterface.ILoginRepository
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.*
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryViewModel
import com.rivaldo.submissionintermediate.ui.login.LoginViewModel
import com.rivaldo.submissionintermediate.ui.main.MainViewModel
import com.rivaldo.submissionintermediate.ui.maps.ListStoryMapsViewModel
import com.rivaldo.submissionintermediate.ui.register.RegisterViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IRegisterRepository> { RegisterRepository(get()) }
    single<ILoginRepository> { LoginRepository(get()) }
    single<IStoriesRepository> { StoriesRepository(get(), get()) }
}

val interactorModule = module {
    factory<LoginUseCase> {
        LoginInteractor(get(), get())
    }

    factory<HomeListStoryUseCase> {
        HomeListStoryInteractor(get(), get())
    }

    factory <AddStoryUseCase> {
        AddStoryInteractor(get(), get())
    }

    factory <StoryMapsUseCase>{
        StoryMapsInteractor(get(), get())
    }

    factory <RegisterUseCase> {
        RegisterInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }
    viewModel { ListStoryMapsViewModel(get())}
}