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
import com.keepcoding.madridshops.adapter.GenericListRecyclerViewAdapter
import com.keepcoding.madridshops.domain.model.Shop
import com.keepcoding.madridshops.domain.model.Shops
import java.io.Serializable

class GenericListFragment <T: Serializable>: Fragment() {
    companion object {
        val ARG_CONTENT = "ARG_CONTENT"

        fun <T: Serializable> newInstance(content: T): GenericListFragment<T> {
            val fragment = GenericListFragment<T>()
            val arguments = Bundle()

            arguments.putSerializable(ARG_CONTENT, content)
            fragment.arguments = arguments

            return fragment
        }

    }

    lateinit var fragmentView: View
    lateinit var recyclerViewContent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            fragmentView = inflater.inflate(R.layout.generic_fragment_list, container, false)
            recyclerViewContent = fragmentView.findViewById(R.id.generic_recycler_view)
            val content = arguments.getSerializable(ARG_CONTENT) as Shops
            val adapter =  GenericListRecyclerViewAdapter(content)
            adapter.onClickListener = View.OnClickListener { view ->
                val position = recyclerViewContent.getChildAdapterPosition(view)
                val contentToShow = content.get(position)
                startActivity(GenericDetailActivity.intent<Shop>(activity, contentToShow))
            }
            recyclerViewContent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewContent.itemAnimator = DefaultItemAnimator()
            recyclerViewContent.adapter = adapter

        }

        return fragmentView


    }

}// Required empty public constructor
