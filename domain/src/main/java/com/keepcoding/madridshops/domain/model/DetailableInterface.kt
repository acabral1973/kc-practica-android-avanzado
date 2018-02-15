package com.keepcoding.madridshops.domain.model

import java.io.Serializable

interface Detailable : Serializable {
    fun get_logo(): String
    fun get_Image(): String
    fun get_Name(): String
    fun get_Address(): String
    fun get_Houres(): String
    fun get_Description(): String
}