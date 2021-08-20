package com.simple.quick.browser.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simple.quick.browser.adapter.HomeNavigationAdapter
import com.simple.quick.browser.model.HomeNavigationModel
import com.simple.quick.browser.ui.activity.MainActivity

/**
 * @author YangJ 首页站点导航控件
 */
class HomeNavigationView : RecyclerView {

    private lateinit var mList: ArrayList<HomeNavigationModel>
    private lateinit var mAdapter: HomeNavigationAdapter

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
        val list = arrayListOf<HomeNavigationModel>()
        val adapter = HomeNavigationAdapter(context, list)
        adapter.setOnItemClickListener(object : HomeNavigationAdapter.OnItemClickListener {
            override fun onItemClick(model: HomeNavigationModel) {
                if (context is MainActivity) {
                    context.onHomeNavigationItemClick(model)
                }
            }
        })
        setAdapter(adapter)
        this.mList = list
        this.mAdapter = adapter
        layoutManager = GridLayoutManager(context, 5)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<HomeNavigationModel>) {
        this.mList.addAll(list)
        this.mAdapter.notifyDataSetChanged()
    }
}