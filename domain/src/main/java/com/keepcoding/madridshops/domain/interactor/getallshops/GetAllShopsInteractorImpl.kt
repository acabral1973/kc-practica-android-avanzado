package com.keepcoding.madridshops.domain.interactor.getallshops

import android.content.Context
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.MadridShopEntity
import com.keepcoding.madridshops.domain.model.MadridShopEntities
import com.keepcoding.madridshops.repository.Repository
import com.keepcoding.madridshops.repository.RepositoryImpl
import com.keepcoding.madridshops.repository.model.DataEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImpl(queryType: String, context: Context) : GetAllShopsInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository : Repository = RepositoryImpl(weakContext.get() !!)
    private val entityType = if (queryType == TypeConstants.SHOP) { TypeConstants.SHOP } else { TypeConstants.ACTIVITY }

    override fun execute(success: SuccessCompletion<MadridShopEntities>, error: ErrorCompletion) {
        repository.getAllEntities(entityType, success = {
            val entities: MadridShopEntities = entityMapper(it)
            success.successCompletion(entities)
        }, error = {
            error(it)
        })
    }

    private fun entityMapper(list: List<DataEntity>): MadridShopEntities {

        val tempList = ArrayList <MadridShopEntity>()
        list.forEach {
            val entity = MadridShopEntity(it.id.toInt(), it.databaseId.toInt(),it.name,it.description_en, it.gps_lat, it.gps_lon,it.img, it.logo_img, it.opening_hours_en, it.address)
            tempList.add(entity)
        }
        val entitiesList = MadridShopEntities(tempList)
        return entitiesList
    }
}