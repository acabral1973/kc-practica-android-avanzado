package com.keepcoding.madridshops.domain.interactor

import com.keepcoding.madridshops.domain.model.MadridShopEntities

typealias CodeClosure = () -> Unit
typealias SuccessClosure = (madridShopEntities: MadridShopEntities) -> Unit
typealias ErrorClosure = (msg: String) -> Unit
typealias Variant = Any
