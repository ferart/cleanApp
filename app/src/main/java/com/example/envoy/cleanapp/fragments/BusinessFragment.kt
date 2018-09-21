package com.example.envoy.cleanapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.domain.exceptions.Failure
import com.example.envoy.cleanapp.R
import com.example.envoy.cleanapp.fragments.utils.failure
import com.example.envoy.cleanapp.fragments.utils.observe
import com.example.envoy.cleanapp.fragments.utils.viewModel
import com.example.envoy.cleanapp.model.BusinessViewEntity
import com.example.envoy.cleanapp.modelview.BusinessViewModel
import kotlinx.android.synthetic.main.fragment_business_list.view.*


class BusinessFragment : Fragment() {

    private var columnCount = 1

    private lateinit var businessViewModel: BusinessViewModel
    private lateinit var adapter: MyBusinessRecyclerViewAdapter
    private var reciclerView: RecyclerView? = null
    private var searchLocationText: TextView? = null
    private var searchTypeText: TextView? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        businessViewModel = viewModel {
            observe(bestBusinessesAround, ::showBusinessesFound)
            failure(failure, ::showError)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_business_list, container, false)
        reciclerView = view.list
        searchLocationText = view.etLocationSearch
        searchTypeText = view.etTypeSearch
        searchButton = view.btSearch
        with(reciclerView) {
            this?.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyBusinessRecyclerViewAdapter(emptyList(), context)
            this?.adapter = adapter
        }

        with(searchButton){
            this?.setOnClickListener {
                if (!searchLocationText?.text.isNullOrEmpty()){
                    businessViewModel.searchBusinessByLocationAndType(searchLocationText?.text.toString(), searchTypeText?.text.toString())
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        businessViewModel.searchBusinessByLocationAndType("Tesla, Irvine, California, US", "tai")
    }


    private fun showBusinessesFound(businesses: List<BusinessViewEntity>?) {
        businesses?.let {
            adapter.setItems(businesses)
        }
    }

    private fun showError(failure: Failure?) {
        //Display Error to the view
    }

    companion object {
        @JvmStatic
        fun newInstance(columnCount: Int) = BusinessFragment()
    }
}
