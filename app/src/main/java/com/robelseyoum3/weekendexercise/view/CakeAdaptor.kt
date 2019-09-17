package com.robelseyoum3.weekendexercise.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robelseyoum3.weekendexercise.R
import com.robelseyoum3.weekendexercise.model.CakeResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cakes_rows.view.*


class CakeAdaptor(private val cakeResult: List<CakeResult>): RecyclerView.Adapter<CakeAdaptor.CakeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        return CakeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_cakes_rows, parent, false))
    }

    override fun getItemCount(): Int {
        return cakeResult.size
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {

        holder.tvCakeName.text =  cakeResult[position].title
        holder.tvDescription.text = cakeResult[position].desc

        Picasso.get().load(cakeResult[position].image).into(holder.tvCakeImage)

    }


    class CakeViewHolder (view: View): RecyclerView.ViewHolder(view){

        val tvCakeName = view.tv_cake_name
        val tvDescription = view.tv_description
        val tvCakeImage = view.cake_image

    }
}