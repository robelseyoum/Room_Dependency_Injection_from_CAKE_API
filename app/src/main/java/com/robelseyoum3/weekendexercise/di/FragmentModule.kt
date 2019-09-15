package com.robelseyoum3.weekendexercise.di

import android.app.Application
import com.robelseyoum3.weekendexercise.network.CakeRequestInterface
import com.robelseyoum3.weekendexercise.viewmodel.CakeViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {
    @Provides
    @Singleton
    fun provideCakeViewModelFactory(cakeRequestInterface: CakeRequestInterface, application: Application): CakeViewModelFactory {
        return CakeViewModelFactory(cakeRequestInterface, application)
    }
}