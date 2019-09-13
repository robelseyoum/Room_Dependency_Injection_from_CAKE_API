package com.robelseyoum3.weekendexercise.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.weekendexercise.network.CakeRequestInterface
import com.robelseyoum3.weekendexercise.network.RetrofitInstances

class CakeViewModelFactory
    (private val cakeRequestInterface: CakeRequestInterface, private val application: Application)
    :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CakeViewModel(cakeRequestInterface, application) as T
    }

}