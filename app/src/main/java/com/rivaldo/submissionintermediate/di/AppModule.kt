package com.rivaldo.submissionintermediate.di

import android.util.Log
import com.rivaldo.submissionintermediate.data.Constant
import com.rivaldo.submissionintermediate.data.remote.api.ApiService
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.repository.RegisterRepository
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import com.rivaldo.submissionintermediate.ui.register.RegisterViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<IRegisterRepository> { RegisterRepository(get()) }
}

val viewModelModule = module{
    viewModel { RegisterViewModel(get()) }
}