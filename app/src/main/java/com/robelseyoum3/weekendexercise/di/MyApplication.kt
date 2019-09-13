package com.robelseyoum3.weekendexercise.di

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .networkModule(NetworkModule(this))
            .build()
            .inject(this)
    }
}
