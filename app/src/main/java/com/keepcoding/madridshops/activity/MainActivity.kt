package com.keepcoding.madridshops.activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.interactor.ErrorCompletion
import com.keepcoding.madridshops.domain.interactor.SuccessCompletion
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractor
import com.keepcoding.madridshops.domain.interactor.getallshops.GetAllShopsInteractorImpl
import com.keepcoding.madridshops.domain.interactor.getallshops.TypeConstants
import com.keepcoding.madridshops.domain.model.MadridShopEntity
import com.keepcoding.madridshops.domain.model.MadridShopEntities
import com.keepcoding.madridshops.fragment.GenericListFragment
import com.keepcoding.madridshops.fragment.GenericMapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initializeEntities(TypeConstants.SHOP)
    }

    fun initializeEntities(entityType: String) {
        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(entityType,this)
        getAllShopsInteractor.execute(success = object: SuccessCompletion<MadridShopEntities>{
            override fun successCompletion(madridShopEntities: MadridShopEntities) {
                initializeListFragment(madridShopEntities)
                initializeMapFragment(madridShopEntities)
            }
        }, error = object : ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, getString(R.string.error_loading_shops), Toast.LENGTH_LONG).show()
            }
        })
    }


    fun initializeMapFragment(madridShopEntities: MadridShopEntities) {

        val mapFragment: GenericMapFragment<MadridShopEntity> = GenericMapFragment.newInstance<MadridShopEntity>(madridShopEntities.all())
        supportFragmentManager.beginTransaction().replace(R.id.map_fragment, mapFragment).commit()
    }

    fun initializeListFragment(madridShopEntities: MadridShopEntities) {
        val listFragment: GenericListFragment<MadridShopEntities> = GenericListFragment.newInstance<MadridShopEntities>(madridShopEntities)
        fragmentManager.beginTransaction().replace(R.id.list_fragment, listFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_shops -> initializeEntities(TypeConstants.SHOP)
            R.id.action_activities -> initializeEntities(TypeConstants.ACTIVITY)
        }
        return true
    }
}
