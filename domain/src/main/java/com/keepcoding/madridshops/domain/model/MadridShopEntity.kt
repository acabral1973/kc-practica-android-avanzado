package com.keepcoding.madridshops.domain.model

import java.io.Serializable

data class MadridShopEntity(val id: Int, val databaseId: Int,
                            val name: String,
                            val description_en: String,
                            val gps_lat: String,
                            val gps_lon: String,
                            val img: String,
                            val logo_img: String,
                            val opening_hours_en: String,
                            val address: String)

    : Mapeable {

    override fun getEntityId(): Int {
        return id
    }

    override fun getEntityDatabaseId(): Int {
        return databaseId
    }

    override fun getEntityLat(): String {
        return gps_lat
    }

    override fun getEntityLon(): String {
        return gps_lon
    }

    override fun getEntityImage(): String {
        return img
    }

    override fun getEntityLogo(): String {
        return logo_img
    }

    override fun getEntityName(): String {
        return name
    }

    override fun getEntityAddress(): String {
        return address
    }

    override fun getEntityHoures(): String {
        return opening_hours_en
    }

    override fun getEntityDescription(): String {
        return description_en
    }

    init {
       MadridShopEntities(ArrayList<MadridShopEntity>())
    }
}

class MadridShopEntities(val madridShopEntities: ArrayList<MadridShopEntity>): Serializable, Aggregate<MadridShopEntity> {

    override fun count(): Int = madridShopEntities.size

    override fun all(): ArrayList<MadridShopEntity> = madridShopEntities

    override fun get(position: Int): MadridShopEntity {
        return madridShopEntities.get(position)
    }

    override fun add(element: MadridShopEntity) {
        madridShopEntities.add(element)
    }

    override fun delete(position: Int) {
        madridShopEntities.removeAt(position)
    }

    override fun delete(element: MadridShopEntity) {
        madridShopEntities.remove(element)
    }
}