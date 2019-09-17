package com.robelseyoum3.weekendexercise.network

import com.robelseyoum3.weekendexercise.common.Constants
import com.robelseyoum3.weekendexercise.model.CakeResult
import io.reactivex.Observable
import retrofit2.http.GET

interface CakeRequestInterface {


    @GET(Constants.CAKES)
    fun getCakes(): Observable<List<CakeResult>>


}