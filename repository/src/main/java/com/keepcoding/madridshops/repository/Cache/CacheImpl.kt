package com.keepcoding.madridshops.repository.Cache

import android.content.Context
import com.keepcoding.madridshops.repository.DispatchOnMainThread
import com.keepcoding.madridshops.repository.db.DBHelper
import com.keepcoding.madridshops.repository.db.build
import com.keepcoding.madridshops.repository.db.dao.ShopDAO
import com.keepcoding.madridshops.repository.model.ShopEntity
import java.lang.ref.WeakReference

internal class CacheImpl(context: Context): Cache{

    val context = WeakReference<Context>(context)

    override fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        // pido todas las shops del cache en segundo plano
        Thread(Runnable {
            // pido todas las tiendas usando DAO (Data Access Object) definido en el package db
            var shops = ShopDAO(cacheDBHelper()).query()

            // una vez ejecutado el borrado vuelvo al hilo principal
            DispatchOnMainThread(Runnable {
                if (shops.count() > 0 ) {
                    success(shops)
                } else {
                    error("No shop in cache")
                }
            })
        }).run()
    }

    override fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        // grabo todas las shops del cache en segundo plano
        Thread(Runnable {
            try {
                // recorro la lista de shops usando DAO (Data Access Object) definido en el package db
                shops.forEach { ShopDAO(cacheDBHelper()).insert(it) }
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {
                DispatchOnMainThread(Runnable {
                    error("Error saving shops in cache")
                })
            }
        }).run()
    }

    override fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit) {

        // mando el borrado de cache en segundo plano
        Thread(Runnable {
            // borro todas las tiendas usando DAO (Data Access Object) definido en el package db
            var successDeleting = ShopDAO(cacheDBHelper()).deleteAll()

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