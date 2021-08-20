package com.simple.quick.browser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simple.quick.browser.R
import com.simple.quick.browser.model.HomeNavigationModel

/**
 * @author YangJ 首页导航站点数据适配器
 */
class HomeNavigationAdapter(context: Context, private val list: ArrayList<HomeNavigationModel>) :
    RecyclerView.Adapter<HomeNavigationAdapter.HomeNavigationViewHolder>() {

    private var mInflater = LayoutInflater.from(context)
    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNavigationViewHolder {
        val view = mInflater.inflate(R.layout.item_home_navigation, parent, false)
        return HomeNavigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeNavigationViewHolder, position: Int) {
        val item = list[position]
        holder.mIvFavicon.setImageResource(R.drawable.ic_launcher_background)
        holder.mTvTitle.text = item.title
        holder.itemView.setOnClickListener {
            this.mListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(model: HomeNavigationModel)
    }

    class HomeNavigationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mIvFavicon: ImageView = itemView.findViewById(R.id.iv_item_home_navigation_favicon)
        var mTvTitle: TextView = itemView.findViewById(R.id.tv_item_home_navigation_title)
    }
}