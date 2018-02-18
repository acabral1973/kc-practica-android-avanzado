package com.keepcoding.madridshops.domain.interactor.deleteAllShops

import android.content.Context
import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure
import com.keepcoding.madridshops.domain.interactor.getallshops.TypeConstants
import com.keepcoding.madridshops.repository.RepositoryImpl
import java.lang.ref.WeakReference

class DeleteAllShopsImpl(queryType: String, context: Context) : DeleteAllShops {

    val weakContext = WeakReference<Context>(context)
    private val entityType = if (queryType == TypeConstants.SHOP) { TypeConstants.SHOP } else { TypeConstants.ACTIVITY }

    override fun execute(success: CodeClosure, error: ErrorClosure) {
        val repository = RepositoryImpl(weakContext.get() !!)

        repository.deleteAllEntities(entityType,success,error)

    }
}