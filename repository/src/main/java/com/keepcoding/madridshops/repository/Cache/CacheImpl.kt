package com.keepcoding.madridshops.repository.Cache

import android.content.Context
import com.keepcoding.madridshops.repository.DispatchOnMainThread
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.db.build
import com.keepcoding.madridshops.repository.db.dao.ShopDAO
import com.keepcoding.madridshops.repository.model.DataEntity
import java.lang.ref.WeakReference

internal class CacheImpl(context: Context): Cache{

    val context = WeakReference<Context>(context)

    override fun getAllEntities(table: String, success: (data: List<DataEntity>) -> Unit, error: (errorMessage: String) -> Unit) {

        // pido todas los datos del cache en segundo plano
        Thread(Runnable {
            // pido todas las tiendas usando DAO (Data Access Object) definido en el package db
            var entities = ShopDAO(cacheDBHelper()).query(table)

            // una vez ejecutado el borrado vuelvo al hilo principal
            DispatchOnMainThread(Runnable {
                if (entities.count() > 0 ) {
                    success(entities)
                } else {
                    error("No data in cache")
                }
            })
        }).run()
    }

    override fun saveAllEntities(table: String, data: List<DataEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // grabo todas las data del cache en segundo plano
        Thread(Runnable {
            try {
                // recorro la lista de data usando DAO (Data Access Object) definido en el package db
                data.forEach { ShopDAO(cacheDBHelper()).insert(it, table) }
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error saving data in cache")
                })
            }
        }).run()
    }

    override fun deleteAllEntities(table: String, success: () -> Unit, error: (errorMessage: String) -> Unit) {

        // mando el borrado de cache en segundo plano
        Thread(Runnable {
            // borro todas las tiendas usando DAO (Data Access Object) definido en el package db
            var successDeleting = ShopDAO(cacheDBHelper()).deleteAll(table)

            // una vez ejecutado el borrado vuelvo al hilo principal
            DispatchOnMainThread(Runnable {
                if (successDeleting) {
                    success()
                } else {
                    error("Error deleting")
                }
            })
        }).run()
    }

    private fun cacheDBHelper(): DBHelper{

        // uso get() porque context es una referencia Weak
        return build(context.get() !!, "MadridShops.sqlite", 1)
    }
}