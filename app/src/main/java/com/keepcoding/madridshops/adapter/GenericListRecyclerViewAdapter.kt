package com.keepcoding.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Aggregate
import com.keepcoding.madridshops.domain.model.Detailable

class GenericListRecyclerViewAdapter<Z: Detailable, T: Aggregate<Z>>(val content: T) : RecyclerView.Adapter<GenericListRecyclerViewAdapter<Z,T>.ContentViewHolder>() {

    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_shop, parent, false)
        view.setOnClickListener(onClickListener)
        return ContentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.count()
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {

        val contentImage= content.get(position).get_Image()
        val contentName = content.get(position).get_Name()
        val contentAddress = content.get(position).get_Address()

        holder?.bindShop(contentImage, contentName, contentAddress)
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // val shopLogo = itemView.findViewById<ImageView>(R.id.shop_logo)
        val shopName = itemView.findViewById<TextView>(R.id.shop_name)
        val shopAddress = itemView.findViewById<TextView>(R.id.shop_address)

        fun bindShop(image: Int, name: String, address: String) {
         //   shopLogo.setImageResource(R.drawable.image_not_available)
            shopName.text = name
            shopAddress.text = address
        }
    }
}