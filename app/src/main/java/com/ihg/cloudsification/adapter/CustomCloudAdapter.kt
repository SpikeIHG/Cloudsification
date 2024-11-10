package com.ihg.cloudsification.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.ihg.cloudsification.R
import com.ihg.cloudsification.entity.CustomCloud


class CustomCloudAdapter(private var items: MutableList<CustomCloud>) : RecyclerView.Adapter<CustomCloudAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val new_name : TextView = view.findViewById(R.id.new_cloud_name)
        val desc: TextView = view.findViewById(R.id.new_species_description)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_cloud, parent, false)
        return ViewHolder(view)

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.desc.text = items[position].descrpiton
        holder.new_name.text=items[position].name

    }

    override fun getItemCount() = items.size

}

