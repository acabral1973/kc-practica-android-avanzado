package com.keepcoding.madridshops.activity

import android.support.multidex.MultiDexApplication
import android.util.Log
import com.keepcoding.madridshops.BuildConfig


class MadridShopsApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // init code application wide

        Log.d("App", BuildConfig.MADRID_SHOPS_SERVER_URL)

        /*

   val allShopsInteractor = GetAllShopsInteractorImpl(this)

   allShopsInteractor.execute(object: SuccessCompletion<MadridShopEntities> {
       override fun successCompletion(madridShopEntities: MadridShopEntities) {
           Log.d("MadridShopEntities", "Success downloading  madridShopEntities - Count: " + madridShopEntities.count())

           madridShopEntities.madridShopEntities.forEach { Log.d("MadridShopEntities", "Downloading data for MadridShopEntity:" +  it.name) }
       }
   }, object: ErrorCompletion {
       override fun errorCompletion(errorMessage: String) {
           Log.d("MadridShopEntities", "Error downloading madridShopEntities : " + errorMessage)
       }
   })




   DeleteAllShopsImpl(this).execute(success = {
       Log.d("MadridShopEntities", "All MadridShopEntities was deleted")
   }, error = {
       Log.d("MadridShopEntities", "Error deleting all MadridShopEntities")
   })

   */

    }


    override fun onLowMemory() {
        super.onLowMemory()
    }
}