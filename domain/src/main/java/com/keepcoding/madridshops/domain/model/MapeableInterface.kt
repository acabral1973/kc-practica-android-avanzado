package com.keepcoding.madridshops.domain.model


import java.io.Serializable

interface Mapeable : Serializable{
    fun get_Name(): String
    fun get_Lat(): String
    fun get_Lon(): String
}