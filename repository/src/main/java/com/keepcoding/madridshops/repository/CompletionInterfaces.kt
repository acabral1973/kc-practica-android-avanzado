package com.keepcoding.madridshops.repository

interface SuccessCompletion<T> {
    fun successCompletion(entity: T)
}

interface ErrorCompletion {
    fun errorCompletion(errorMessage: String)
}