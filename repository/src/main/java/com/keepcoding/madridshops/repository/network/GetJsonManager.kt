package com.keepcoding.madridshops.repository.network

import com.keepcoding.madridshops.repository.ErrorCompletion
import com.keepcoding.madridshops.repository.SuccessCompletion

interface GetJsonManager {
    fun execute(url: String, success: SuccessCompletion<String>, error: ErrorCompletion)
}