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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.keepcoding.madridshops.activity.GenericDetailActivity
import com.keepcoding.madridshops.domain.model.Mapeable

class GenericMapFragment<T: Mapeable>: SupportMapFragment() {
    companion object {
        private val ARG_CONTENT = "ARG_CONTENT"

        public fun <T: Mapeable> newInstance(content: ArrayList<T>): GenericMapFragment<T> {

            val fragment = GenericMapFragment<T>()
            val arguments = Bundle()

            arguments.putSerializable(ARG_CONTENT, content)
            fragment.arguments = arguments

            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        val content = arguments.getSerializable(ARG_CONTENT) as ArrayList<T>

        setupMap(content)

        return fragmentView
    }


    private fun setupMap(content: ArrayList<T> ){

        getMapAsync({ map: GoogleMap ->
            Log.d("MapShops", "Mapa inicializado")
            centerMapInPosition(map, 40.416775, -3.703790)
            map.uiSettings.isRotateGesturesEnabled = false
            map.uiSettings.isMyLocationButtonEnabled = true
            map.uiSettings.isZoomControlsEnabled = true
            map.uiSettings.isMapToolbarEnabled = false
            map.uiSettings.isZoomGesturesEnabled = false
            showUserPosition(activity, map)
            addPointsToMap(content, map)
        })
    }

    private fun centerMapInPosition(map: GoogleMap, latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder().target(LatLng(latitude, longitude)).zoom(17f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun showUserPosition(context: Context, map: GoogleMap){

        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 10)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: SnackBar para reiterar que los permisos son necesarios si no los ha dado

        } else {
            map.isMyLocationEnabled = true
        }
    }

    private fun addPinToMap(map: GoogleMap, elementToShow: T) {

        val name = elementToShow.getEntityName()
        val address = elementToShow.getEntityAddress()
        val lat = elementToShow.getEntityLat().toDouble()
        val lon = elementToShow.getEntityLon().toDouble()

        val mappedMarker = map.addMarker(MarkerOptions().position(LatLng(lat,lon)).title(name).snippet(address))
        mappedMarker.tag = elementToShow

        map.setOnInfoWindowClickListener {
            onInfoWindowClick(it)
        }

    }

    private fun addPointsToMap(content: ArrayList<T>, map: GoogleMap){

        val pointsQuantity = content.count()

        for (i in 1 until pointsQuantity) {
            addPinToMap(map, content.get(i))
        }
    }

    private fun onInfoWindowClick(marker: Marker) {

        val elementToShow = marker.tag as T
        startActivity(GenericDetailActivity.intent<T>(activity, elementToShow))
    }

}