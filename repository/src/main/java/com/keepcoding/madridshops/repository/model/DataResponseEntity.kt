package com.keepcoding.madridshops.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
internal class DataResponseEntity(
    val result: MutableList<DataEntity>
)
