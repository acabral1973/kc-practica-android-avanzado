package com.keepcoding.madridshops.domain.interactor.getallshops

import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.MadridShopEntities

interface GetAllShopsInteractor {
    fun execute(success: SuccessCompletion<MadridShopEntities>, error: ErrorCompletion)
}