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
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import com.keepcoding.madridshops.fragment.GenericListFragment
import com.keepcoding.madridshops.fragment.GenericMapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val getAllShopsInteractor: GetAllShopsInteractor = GetAllShopsInteractorImpl(this)
        getAllShopsInteractor.execute(success = object: SuccessCompletion<Shops>{
            override fun successCompletion(shops: Shops) {
                initializeListFragment(shops)
                initializeMapFragment(shops)
            }
        }, error = object : ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast.makeText(baseContext, getString(R.string.error_loading_shops), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun initializeMapFragment(shops: Shops) {

        val mapFragment: GenericMapFragment = GenericMapFragment.newInstance<Shop>(shops.all())
        supportFragmentManager.beginTransaction().replace(R.id.map_fragment, mapFragment).commit()
    }

    fun initializeListFragment(shops: Shops) {
        val listFragment: GenericListFragment<Shops> = GenericListFragment.newInstance<Shops>(shops)
        fragmentManager.beginTransaction().replace(R.id.list_fragment, listFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
