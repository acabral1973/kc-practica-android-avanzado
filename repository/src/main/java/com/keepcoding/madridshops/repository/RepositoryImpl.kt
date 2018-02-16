package com.keepcoding.madridshops.repository

import android.content.Context
import com.android.volley.Response.success
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.keepcoding.madridshops.repository.Cache.Cache
import com.keepcoding.madridshops.repository.Cache.CacheImpl
import com.keepcoding.madridshops.repository.db.DBConstants
import com.keepcoding.madridshops.repository.model.DataEntity
import com.keepcoding.madridshops.repository.model.DataResponseEntity
import com.keepcoding.madridshops.repository.network.GetJsonManager
import com.keepcoding.madridshops.repository.network.GetJsonManagerVolleyImpl
import com.keepcoding.madridshops.repository.network.json.JsonEntitiesParser
import madridshops.keepcoding.com.repository.BuildConfig
import java.lang.ref.WeakReference

class RepositoryImpl(context: Context): Repository{

    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache = CacheImpl(weakContext.get() !!)

    override fun getAllEntities(entityType: String, success: (data: List<DataEntity>) -> Unit, error: (errorMessage: String) -> Unit) {

        var table: String

        if (entityType == DBConstants.TABLE_SHOP) {
            table = DBConstants.TABLE_SHOP
        } else {
            table = DBConstants.TABLE_ACTIVITY
        }

        cache.getAllEntities(table, success = {
            // pido todas las tiendas al cache y si las hay las devuelvo
            success(it)
        }, error = {
            // si no hay tiendas en el cache voy a descargar
            populateCache(entityType, success, error)
        })

    }

    private fun populateCache(entityType: String, success: (data: List<DataEntity>) -> Unit, error: (errorMessage: String) -> Unit) {

        // hago una petición de datos a la red dependiendo del tipo de entidad uso URL de Shops o de Activities
        var url: String
        var table: String

        if (entityType == DBConstants.TABLE_SHOP) {
            url = BuildConfig.MADRID_SHOPS_SERVER_URL
            table = DBConstants.TABLE_SHOP
        } else {
            table = DBConstants.TABLE_ACTIVITY
            url = BuildConfig.MADRID_ACTIVITIES_SERVER_URL
        }

        val jsonManager : GetJsonManager = GetJsonManagerVolleyImpl(weakContext.get() !!)
        jsonManager.execute(url, success = object : SuccessCompletion<String> {
            override fun successCompletion(shopsJson: String) {

                val parser = JsonEntitiesParser()
                var responseEntity: DataResponseEntity
                try {
                    responseEntity = parser.parse<DataResponseEntity>(shopsJson)
                } catch (error: InvalidFormatException) {
                    error("Error parseando datos")
                    return
                }


                // limpio el parseo ignorando registros con latitudes y/o longitudes incorrectas
                val cleanedResponseEntity = DataResponseEntity(ArrayList())
                for (i in 0 until responseEntity.result.count()){
                    val shopEntity = responseEntity.result[i]
                    if (!errorOnLatOrLon(shopEntity)) {
                        cleanedResponseEntity.result.add(responseEntity.result[i])
                    }
                }

                // guardo el resultado en cache
                cache.saveAllEntities(table, cleanedResponseEntity.result, success = {
                    success(cleanedResponseEntity.result)
                }, error = {
                    error("Error guardando datos en el cache")
                })
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }
        })
    }

    private fun errorOnLatOrLon(dataEntity: DataEntity): Boolean {
        // devuelve error si vienen valores incorrectos para latitud o longitud
        return dataEntity.gps_lat.contains(",") || dataEntity.gps_lat.isEmpty() || dataEntity.gps_lon.contains(",") || dataEntity.gps_lon.isEmpty()
    }

    override fun deleteAllEntities(entityType: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {

        // le digo al cache que borre todos los datos de la tabla que corresponda
        var table: String

        if (entityType == DBConstants.TABLE_SHOP) {
            table = DBConstants.TABLE_SHOP
        } else {
            table = DBConstants.TABLE_ACTIVITY
        }
        cache.deleteAllEntities(table, success, error)
    }

}