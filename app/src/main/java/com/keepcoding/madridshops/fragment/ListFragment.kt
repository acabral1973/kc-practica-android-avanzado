package com.keepcoding.madridshops.fragment


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.activity.GenericDetailActivity
import com.keepcoding.madridshops.adapter.ShopListRecyclerViewAdapter
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops

class ListFragment : Fragment() {
    companion object {
        val ARG_SHOPS = "ARG_SHOPS"

        fun newInstance(shops: Shops): ListFragment {
            val fragment = ListFragment()
            val arguments = Bundle()

            arguments.putSerializable(ARG_SHOPS, shops)
            fragment.arguments = arguments

            return fragment
        }

    }

    lateinit var fragmentView: View
    lateinit var recyclerViewShops: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater.inflate(R.layout.fragment_list, container, false)
            recyclerViewShops = fragmentView.findViewById<RecyclerView>(R.id.recycler_view_shops)
            val shops = arguments.getSerializable(ARG_SHOPS) as Shops
            val adapter =  ShopListRecyclerViewAdapter(shops)
            adapter.onClickListener = View.OnClickListener { view ->
                val position = recyclerViewShops.getChildAdapterPosition(view)
                val shopToShow = shops.get(position)
                startActivity(GenericDetailActivity.intent<Shop>(activity, shopToShow))
            }
            recyclerViewShops.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewShops.itemAnimator = DefaultItemAnimator()
            recyclerViewShops.adapter = adapter

        }

        return fragmentView


    }

}// Required empty public constructor
