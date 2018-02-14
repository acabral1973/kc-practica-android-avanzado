package com.keepcoding.madridshops.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.keepcoding.madridshops.domain.model.Mapeable

class GenericMapFragment: SupportMapFragment() {
    companion object {
        private val ARG_NAME = "ARG_NAME"
        private val ARG_LAT = "ARG_LAT"
        private val ARG_LON = "ARG_LON"

        public fun <T: Mapeable> newInstance(content: List<T>): GenericMapFragment  {
            val fragment = GenericMapFragment()
            val arguments = Bundle()
            val nameList = ArrayList<String>()
            val latList = ArrayList<String>()
            val lonList = ArrayList<String>()

            for (i in 0 until content.count()) {
                nameList.add(content[i].get_Name())
                latList.add(content[i].get_Lat())
                lonList.add(content[i].get_Lon())
            }

            arguments.putStringArrayList(ARG_NAME, nameList)
            arguments.putStringArrayList(ARG_LAT, latList)
            arguments.putStringArrayList(ARG_LON, lonList)
            fragment.arguments = arguments

            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        val nameList: ArrayList<String> = arguments.getStringArrayList(ARG_NAME)
        val latList: ArrayList<String> = arguments.getStringArrayList(ARG_LAT)
        val lonList: ArrayList<String> = arguments.getStringArrayList(ARG_LON)

        setupMap(nameList, latList, lonList)

        return fragmentView
    }


    private fun setupMap(nameList: List<String>, latList: List<String>, lonList: List<String> ){

        getMapAsync({ map: GoogleMap ->
            Log.d("MapShops", "Mapa inicializado")
            centerMapInPosition(map, 40.416775, -3.703790)
            map.uiSettings.isRotateGesturesEnabled = false
            map.uiSettings.isMyLocationButtonEnabled = true
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isMapToolbarEnabled = false
            map.uiSettings.isZoomGesturesEnabled = false
            showUserPosition(activity, map)
            addPointsToMap(nameList, latList, lonList, map)
        })
    }

    fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder().target(LatLng(latitude, longitude)).zoom(17f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun showUserPosition(context: Context, map: GoogleMap){

        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 10)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: SnackBar para reiterar que los permisos son necesarios si no los ha dado

        } else {
            map.isMyLocationEnabled = true
        }
    }

    fun addPinToMap(map: GoogleMap, latitude: Double, longitude: Double, title: String) {
        map.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).title(title))
    }

    fun addPointsToMap(nameList: List<String>, latList: List<String>, lonList: List<String>, map: GoogleMap){

        val pointsQuantity = nameList.count()

        for (i in 1 until pointsQuantity) {
            val name = nameList[i]
            val lat = latList[i].toDouble()
            val lon = lonList[i].toDouble()

            addPinToMap(map, lat, lon, name)
        }
    }



}