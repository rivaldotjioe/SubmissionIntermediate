package com.rivaldo.submissionintermediate.di


import com.rivaldo.core.domain.interactor.*
import com.rivaldo.core.domain.useCase.*
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryViewModel
import com.rivaldo.submissionintermediate.ui.login.LoginViewModel
import com.rivaldo.submissionintermediate.ui.main.MainViewModel
import com.rivaldo.submissionintermediate.ui.maps.ListStoryMapsViewModel
import com.rivaldo.submissionintermediate.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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