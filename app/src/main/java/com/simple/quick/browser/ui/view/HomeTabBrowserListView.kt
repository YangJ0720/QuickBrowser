package com.simple.quick.browser.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simple.quick.browser.R
import com.simple.quick.browser.adapter.HomeTabBrowserListAdapter
import com.simple.quick.browser.core.TabBrowser

class HomeTabBrowserListView: LinearLayout {

    private lateinit var mList: ArrayList<TabBrowser>
    private lateinit var mAdapter: HomeTabBrowserListAdapter
    private var mCallback: Callback? = null

    constructor(context: Context) : super(context) {
        initialize(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context)
    }

    private fun initialize(context: Context) {
        orientation = VERTICAL
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_tab_browser_list, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val list = arrayListOf<TabBrowser>()
        val adapter = HomeTabBrowserListAdapter(context, list)
        recyclerView.adapter = adapter
        this.mList = list
        this.mAdapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        view.findViewById<View>(R.id.tv_home_tab_browser_back).setOnClickListener {

        }
        view.findViewById<View>(R.id.tv_home_tab_browser_add).setOnClickListener {
            this.mCallback?.add()
        }
        view.findViewById<View>(R.id.tv_home_tab_browser_clear).setOnClickListener {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<TabBrowser>) {
        this.mList.addAll(list)
        this.mAdapter.notifyDataSetChanged()
    }

    fun setCallback(callback: Callback) {
        this.mCallback = callback
    }

    interface Callback {
        fun back()
        fun add()
        fun clear()
    }

}