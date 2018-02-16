package com.keepcoding.madridshops.repository.Cache

import com.keepcoding.madridshops.repository.model.DataEntity

internal interface Cache {
    fun getAllEntities(table: String, success: (data: List<DataEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun saveAllEntities(table: String, data: List<DataEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllEntities(table: String, success: () -> Unit, error: (errorMessage: String) -> Unit)
}