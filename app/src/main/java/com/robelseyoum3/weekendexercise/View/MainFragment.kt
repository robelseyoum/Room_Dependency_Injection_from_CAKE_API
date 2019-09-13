package com.robelseyoum3.weekendexercise.View


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.robelseyoum3.weekendexercise.R
import com.robelseyoum3.weekendexercise.di.DaggerAppComponent
import com.robelseyoum3.weekendexercise.di.MyApplication
import com.robelseyoum3.weekendexercise.di.NetworkModule
import com.robelseyoum3.weekendexercise.model.CakeResult
import com.robelseyoum3.weekendexercise.viewmodel.CakeViewModel
import com.robelseyoum3.weekendexercise.viewmodel.CakeViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {


    @Inject
    lateinit var cakeViewModelFactory: CakeViewModelFactory

    private lateinit var viewModel: CakeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerAppComponent.builder()
            .networkModule(NetworkModule(activity!!.application as MyApplication))
            .build()
            .inject(this)

        viewModel = ViewModelProviders.of(this, cakeViewModelFactory).get(CakeViewModel::class.java)


        viewModel.getCakeData()

        viewModel.returnResult()?.observe(this,
            Observer<List<CakeResult>> {
                    t ->
                Log.i(TAG, ""+t!![1].title)
                cakeAdapterData(t)
            })


        viewModel.getAllDBCakes()

        viewModel.returnDBResult()?.observe(this, object : Observer<List<CakeResult>>{
            override fun onChanged(t: List<CakeResult>?) {
                Log.i(TAG, "Rooo"+t!![2].title)
                cakeAdapterData(t)
            }
        })

        viewModel.returnProgressBar()?.observe(this, object : Observer<Boolean>{
            override fun onChanged(t: Boolean?) {
               if(t==true){
                   progress_id_main.visibility = View.VISIBLE
               }else{
                   progress_id_main.visibility = View.GONE
               }
            }
        })

        viewModel.returnErrorResult()?.observe(this, object : Observer<Boolean>{
            override fun onChanged(t: Boolean?) {

                if(t == true){
                    Toast.makeText(activity,"Show error page",Toast.LENGTH_SHORT).show()
                    include_error_msg.visibility = View.VISIBLE
                }else{
                    include_error_msg.visibility = View.GONE
                }
            }

        })

        viewModel.showDbSuccess.observe(this, Observer {
            if (it == true){
                Toast.makeText(context,"got Cake Database successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Something went wrong with db",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cakeAdapterData(result: List<CakeResult>){
      val adaptor = CakeAdaptor(result)
      rvList.layoutManager = LinearLayoutManager(activity?.applicationContext) //as RecyclerView.LayoutManager?
      rvList.adapter = adaptor
    }

    companion object{
        const val TAG = "MainActivity-RobaZmaks"
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }


}
