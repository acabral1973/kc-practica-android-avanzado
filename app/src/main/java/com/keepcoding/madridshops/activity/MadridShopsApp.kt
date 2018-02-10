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

   allShopsInteractor.execute(object: SuccessCompletion<Shops> {
       override fun successCompletion(shops: Shops) {
           Log.d("Shops", "Success downloading  shops - Count: " + shops.count())

           shops.shops.forEach { Log.d("Shops", "Downloading data for Shop:" +  it.name) }
       }
   }, object: ErrorCompletion {
       override fun errorCompletion(errorMessage: String) {
           Log.d("Shops", "Error downloading shops : " + errorMessage)
       }
   })




   DeleteAllShopsImpl(this).execute(success = {
       Log.d("Shops", "All Shops was deleted")
   }, error = {
       Log.d("Shops", "Error deleting all Shops")
   })

   */

    }


    override fun onLowMemory() {
        super.onLowMemory()
    }
}