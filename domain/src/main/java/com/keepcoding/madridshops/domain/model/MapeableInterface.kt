package com.keepcoding.madridshops.domain.model

import java.io.Serializable

interface Mapeable : Serializable{
    fun getEntityId(): Int
    fun getEntityDatabaseId(): Int
    fun getEntityName(): String
    fun getEntityAddress(): String
    fun getEntityHoures(): String
    fun getEntityDescription(): String
    fun getEntityLat(): String
    fun getEntityLon(): String
    fun getEntityLogo(): String
    fun getEntityImage(): String
}