package com.keepcoding.madridshops.repository

import com.keepcoding.madridshops.repository.model.DataEntity

interface Repository {
    fun getAllEntities(entityType: String, success: (data: List<DataEntity>) -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllEntities(entityType: String, success: () -> Unit, error: (errorMessage: String) -> Unit)
}