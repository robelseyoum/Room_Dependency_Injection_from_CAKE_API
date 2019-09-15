package com.robelseyoum3.weekendexercise.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robelseyoum3.weekendexercise.model.CakeDatabase
import com.robelseyoum3.weekendexercise.model.CakeResult
import com.robelseyoum3.weekendexercise.network.CakeRequestInterface
import com.robelseyoum3.weekendexercise.network.RetrofitInstances
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//class CakeViewModel: ViewModel() {
//class CakeViewModel @Inject constructor(val cakeRequestInterface: CakeRequestInterface, application: Application) : AndroidViewModel(application) {
class CakeViewModel @Inject constructor
    (private val cakeRequestInterface: CakeRequestInterface, application: Application) : ViewModel() {


    private var cakeMutableData:  MutableLiveData<List<CakeResult>>? = MutableLiveData()
    private var cakeDBMutableData:  MutableLiveData<List<CakeResult>>? = MutableLiveData()



    private var progressbarMutableData:  MutableLiveData<Boolean>? = MutableLiveData()

    private var errorMessagePage: MutableLiveData<Boolean>? = MutableLiveData()

    var showSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var showDbSuccess: MutableLiveData<Boolean> = MutableLiveData()

    /*
    var showUsers: MutableLiveData<List<User>> = MutableLiveData()...//cakeMutableData
    var showSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var showDbSuccess: MutableLiveData<Boolean> = MutableLiveData()
     */

    var compositeDisposable = CompositeDisposable() //we can add several observable


    var cakeDao = CakeDatabase.getDatabase(application)?.CakeDAO()


    fun getCakeData(){

        progressbarMutableData?.value = true

        Log.i(TAG, "GET Cake result ")
        //this one is created at di.NetworkModule/fun provideClientInterface(retrofit: Retrofit) = retrofit.create(CakeRequestInterface::class.java)

//        val cakeRequest = RetrofitInstances().retrofitInstances.create(CakeRequestInterface::class.java)
//        val call = cakeRequest.getCakes()
        val call = cakeRequestInterface.getCakes()

        compositeDisposable.add(

            call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }



//    fun isOnline(activity: AppCompatActivity):Boolean{
//        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo=connectivityManager.activeNetworkInfo
//        return networkInfo!=null && networkInfo.isConnected
//    }

//
//    fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
//        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo=connectivityManager.activeNetworkInfo
//        return networkInfo!=null && networkInfo.isConnected
//    }

//    fun isConnectedToInternet(): Boolean {
//        val connectivity = context.getSystemService(
//            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (connectivity != null) {
//            val info = connectivity.allNetworkInfo
//            if (info != null)
//                for (i in info.indices)
//                    if (info[i].state == NetworkInfo.State.CONNECTED) {
//                        return true
//                    }
//        }
//        return false
//    }


    //Log.i("Handle TITLE  ", ""+res[0].title)


    private fun handleResponse(result: List<CakeResult>) {

        var res = result
        cakeMutableData?.value = res

        progressbarMutableData?.value = false
        errorMessagePage?.value = false

        //telling the database is connection is success
        showSuccess.value = true

        addToDb(res)
    }

    private fun addToDb(result: List<CakeResult>) {

            compositeDisposable.add(
                cakeDao!!.insertCake(result)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {showDbSuccess.value = true}
            )

    }

    fun getAllDBCakes(){

        compositeDisposable.add(
            cakeDao!!.getAllCakes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                    cake -> cakeDBMutableData!!.value = cake
                    },{}
                )
        )
    }

    fun returnDBResult(): MutableLiveData<List<CakeResult>>?{
        return cakeDBMutableData
    }

    fun returnResult(): MutableLiveData<List<CakeResult>>?{
        progressbarMutableData?.value = true
        return cakeMutableData
    }

    fun returnProgressBar(): MutableLiveData<Boolean>?{
        return progressbarMutableData
    }


    private fun handleError(error: Throwable) {
        Log.d("Actors Error ", ""+error.message)
        errorMessagePage?.value = true
        progressbarMutableData?.value = false

        //telling the database is connection is success
        showSuccess.value = false

    }

    fun returnErrorResult(): MutableLiveData<Boolean>?{
        return errorMessagePage
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }


    companion object{
        const val TAG = "CakeViewModel"
    }


     fun onDestroy() {
        compositeDisposable.clear()
    }

}