package com.keepcoding.madridshops.domain.model


import java.io.Serializable

interface Mapeable : Serializable{
    fun getPoint(): PointToMap
}