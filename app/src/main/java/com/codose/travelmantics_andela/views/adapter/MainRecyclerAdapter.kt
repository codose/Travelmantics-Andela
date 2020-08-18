package com.codose.travelmantics_andela.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codose.travelmantics_andela.R
import com.codose.travelmantics_andela.models.TravelMantic
import kotlinx.android.synthetic.main.item_main_item.view.*

/*
Created by
Oshodin Osemwingie

on 18/08/2020.
*/
class MainRecyclerAdapter(val context : Context, val clickListener: MainClickListener) : ListAdapter<TravelMantic, MainRecyclerAdapter.MyViewHolder>(
    MainDiffCallback()
) {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            item: TravelMantic,
            clickListener: MainClickListener
        ) {
            itemView.setOnClickListener {
                clickListener.onClick(item)
            }
            itemView.travel_title_item.text = item.placeName
            itemView.travel_description_item.text = item.description
            Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.circle_btn)
                .into(itemView.travel_image_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }
    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_main_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context,item,clickListener)
    }
}

class MainClickListener(val clickListener: (main : TravelMantic) -> Unit){
    fun onClick(main: TravelMantic) = clickListener(main)
}

class MainDiffCallback : DiffUtil.ItemCallback<TravelMantic>(){
    override fun areItemsTheSame(oldItem: TravelMantic, newItem: TravelMantic): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TravelMantic, newItem: TravelMantic): Boolean {
        return oldItem == newItem
    }
}
