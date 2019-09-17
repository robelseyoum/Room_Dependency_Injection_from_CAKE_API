package com.robelseyoum3.weekendexercise.di

import com.robelseyoum3.weekendexercise.view.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(FragmentModule::class, NetworkModule::class))
interface FragmentComponent {
    fun inject(mainFragment: MainFragment)
}