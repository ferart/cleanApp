package com.example.envoy.cleanapp.fragments


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.envoy.cleanapp.R
import com.example.envoy.cleanapp.model.BusinessViewEntity
import kotlinx.android.synthetic.main.search_row.view.*


class MyBusinessRecyclerViewAdapter(
        private var values: List<BusinessViewEntity>,
        private var context: Context?)
    : RecyclerView.Adapter<MyBusinessRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        holder.type.text = item.businessType
        holder.rating.text = item.businessType
        holder.rating.text = item.rating?.toString()
        holder.location.text = "${item.street}"
        context?.let {
            Glide.with(it)
                    .load(item.urlPhoto)
                    .into(holder.businessImage)
        }
        with(holder.view) {
            tag = item
        }
    }

    fun setItems(items: List<BusinessViewEntity>) {
        values = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var businessImage: ImageView = view.ivBusiness
        val nameView: TextView = view.tvBusinessName
        val type: TextView = view.tvBusinessType
        val rating: TextView = view.tvRating
        val location: TextView = view.tvLocation
    }
}
