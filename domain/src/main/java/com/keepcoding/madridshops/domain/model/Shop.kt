package com.keepcoding.madridshops.domain.model

import java.io.Serializable
import java.util.*

data class Shop (val id: Int, val databaseId: Int,
                 val name: String,
                 val description_en: String,
                 val gps_lat: String,
                 val gps_lon: String,
                 val img: String,
                 val logo_img: String,
                 val opening_hours_en: String,
                 val address: String)

    : Detailable, Mapeable {

    override fun get_Lat(): String {
        return gps_lat
    }

    override fun get_Lon(): String {
        return gps_lon
    }


    override fun get_Image(): Int {
        return 0
    }

    override fun get_Name(): String {
        return name
    }

    override fun get_Address(): String {
        return address
    }

    override fun get_Houres(): String {
        return opening_hours_en
    }

    override fun get_Description(): String {
        return description_en
    }

    init {
       Shops(ArrayList<Shop>())
    }
}

class Shops(val shops: MutableList<Shop>): Serializable, Aggregate<Shop> {

    override fun count(): Int = shops.size

    override fun all(): List<Shop> = shops

    override fun get(position: Int): Shop {
        return shops.get(position)
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }
}