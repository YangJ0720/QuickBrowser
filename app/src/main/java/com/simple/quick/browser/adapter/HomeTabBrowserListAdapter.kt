package com.simple.quick.browser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simple.quick.browser.R
import com.simple.quick.browser.core.TabBrowser

class HomeTabBrowserListAdapter(context: Context, private val list: List<TabBrowser>) :
    RecyclerView.Adapter<HomeTabBrowserListAdapter.HomeTabBrowserListViewHolder>() {

    private val mInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTabBrowserListViewHolder {
        val view = mInflater.inflate(R.layout.item_home_tab_browser_list, parent, false)
        return HomeTabBrowserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeTabBrowserListViewHolder, position: Int) {
        val item = list[position]
        holder.mTvTitle.text = item.getUrl()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeTabBrowserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTvTitle: TextView = itemView.findViewById(R.id.tv_item_home_tab_browser_list)
    }
}