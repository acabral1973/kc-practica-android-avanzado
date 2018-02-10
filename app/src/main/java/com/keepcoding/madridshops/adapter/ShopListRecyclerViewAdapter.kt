package com.keepcoding.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Shops

class ShopListRecyclerViewAdapter(val shops: Shops) : RecyclerView.Adapter<ShopListRecyclerViewAdapter.ShopViewHolder>() {

    var onClickListener : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cell_shop, parent, false)
        view.setOnClickListener(onClickListener)
        return ShopViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shops.count()
    }

    override fun onBindViewHolder(holder: ShopViewHolder?, position: Int) {

        val shopLogo = shops.get(position).logo_img
        val shopName = shops.get(position).name
        val shopAddress = shops.get(position).address

        holder?.bindShop(shopLogo, shopName, shopAddress)
    }

    inner class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shopLogo = itemView.findViewById<ImageView>(R.id.shop_logo)
        val shopName = itemView.findViewById<TextView>(R.id.shop_name)
        val shopAddress = itemView.findViewById<TextView>(R.id.shop_address)

        fun bindShop(image: String, name: String, address: String) {
            shopLogo.setImageResource(R.drawable.image_not_available)
            shopName.text = name
            shopAddress.text = address
        }
    }
}