package com.keepcoding.madridshops.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Mapeable
import java.io.Serializable

class GenericMapFragment <T: Mapeable>: Fragment() {
    companion object {
        private val ARG_CONTENT = "ARG_CONTENT"

        fun <T: Serializable> newInstance(content: T): GenericMapFragment<T> {
            val fragment = GenericMapFragment<T>()
            val arguments = Bundle()

            arguments.putSerializable(ARG_CONTENT, content)
            fragment.arguments = arguments

            return fragment
        }

    }

    lateinit var fragmentView: View
    lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater.inflate(R.layout.generic_fragment_map, container, false)

            val contentMap = arguments.getSerializable(ARG_CONTENT) as T


        }

        return fragmentView


    }

}