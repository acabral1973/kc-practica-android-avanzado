package com.keepcoding.madridshops.repository

import android.content.Context
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.keepcoding.madridshops.repository.Cache.Cache
import com.keepcoding.madridshops.repository.Cache.CacheImpl
import com.keepcoding.madridshops.repository.model.ShopEntity
import com.keepcoding.madridshops.repository.model.ShopsResponseEntity
import com.keepcoding.madridshops.repository.network.GetJsonManager
import com.keepcoding.madridshops.repository.network.GetJsonManagerVolleyImpl
import com.keepcoding.madridshops.repository.network.json.JsonEntitiesParser
import madridshops.keepcoding.com.repository.BuildConfig
import java.lang.ref.WeakReference

class RepositoryImpl(context: Context): Repository{

    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache = CacheImpl(weakContext.get() !!)

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {

        cache.getAllShops(success = {
            // pido todas las tiendas al cache y si las hay las devuelvo
            success(it)
        }, error = {
            // si no hay tiendas en el cache voy a descargar
            populateCache(success, error)
        })

    }

    private fun populateCache(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // hago una petición de datos a la red

        val jsonManager : GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(BuildConfig.MADRID_ACTIVITIES_SERVER_URL, success = object : SuccessCompletion<String> {
            override fun successCompletion(shopsJson: String) {

                val parser = JsonEntitiesParser()
                var responseEntity: ShopsResponseEntity
                try {
                    responseEntity = parser.parse<ShopsResponseEntity>(shopsJson)
                } catch (error: InvalidFormatException) {
                    error("Error parseando datos")
                    return
                }


                // limpio el parseo ignorando registros con latitudes y/o longitudes incorrectas
                val cleanedResponseEntity = ShopsResponseEntity(ArrayList())
                for (i in 0 until responseEntity.result.count()){
                    val shopEntity = responseEntity.result[i]
                    if (!errorOnLatOrLon(shopEntity)) {
                        cleanedResponseEntity.result.add(responseEntity.result[i])
                    }
                }

                // guardo el resultado en cache
                cache.saveAllShops(cleanedResponseEntity.result, success = {
                    success(cleanedResponseEntity.result)
                }, error = {
                    error("Error guardando shops en el cache")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }

    private fun errorOnLatOrLon(shopEntity: ShopEntity): Boolean {
        // devuelve error si vienen valores incorrectos para latitud o longitud
        return shopEntity.gps_lat.contains(",") || shopEntity.gps_lat.isEmpty() || shopEntity.gps_lon.contains(",") || shopEntity.gps_lon.isEmpty()
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // le digo al cache que borre todo
        cache.deleteAllShops(success, error)

    }

}