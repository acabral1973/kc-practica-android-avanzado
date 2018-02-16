package com.keepcoding.madridshops.domain.interactor.getallshops

import android.content.Context
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.keepcoding.madridshops.repository.Repository
import com.keepcoding.madridshops.repository.RepositoryImpl
import com.keepcoding.madridshops.repository.model.DataEntity
import java.lang.ref.WeakReference

class GetAllShopsInteractorImpl(context: Context) : GetAllShopsInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository : Repository = RepositoryImpl(weakContext.get() !!)

    override fun execute(success: SuccessCompletion<Shops>, error: ErrorCompletion) {
        repository.getAllShops(success = {
            val shops: Shops = entityMapper(it)
            success.successCompletion(shops)
        }, error = {
            error(it)
        })
    }

    private fun entityMapper(list: List<DataEntity>): Shops {

        val tempList = ArrayList <Shop>()
        list.forEach {
            val shop = Shop(it.id.toInt(), it.databaseId.toInt(),it.name,it.description_en, it.gps_lat, it.gps_lon,it.img, it.logo_img, it.opening_hours_en, it.address)
            tempList.add(shop)
        }
        val shops = Shops(tempList)
        return shops

    }
}