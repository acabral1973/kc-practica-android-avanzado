package com.keepcoding.madridshops.domain.interactor.deleteAllShops

import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure

interface DeleteAllShops {
    fun execute(success: CodeClosure, error: ErrorClosure)
}