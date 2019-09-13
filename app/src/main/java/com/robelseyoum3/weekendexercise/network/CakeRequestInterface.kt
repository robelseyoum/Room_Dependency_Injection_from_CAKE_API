package com.robelseyoum3.weekendexercise.network

import android.provider.SyncStateContract
import com.robelseyoum3.weekendexercise.Common.Constants
import com.robelseyoum3.weekendexercise.model.CakeResult
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CakeRequestInterface {


    @GET(Constants.CAKES)
    fun getCakes(): Observable<List<CakeResult>>


}