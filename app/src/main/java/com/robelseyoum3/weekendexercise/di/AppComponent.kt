package com.robelseyoum3.weekendexercise.di

import com.robelseyoum3.weekendexercise.View.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {

    fun inject(myApplication: MyApplication)
    //fun inject(mainFragment: MainFragment)

}