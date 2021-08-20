package com.simple.quick.browser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simple.quick.browser.R
import com.simple.quick.browser.model.HomeNavigationModel
import com.simple.quick.browser.ui.view.HomeNavigationView

/**
 * @author YangJ
 */
class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val homeNavigationView = view.findViewById<HomeNavigationView>(R.id.homeNavigationView)
        homeNavigationView.setData(getData())
        return view
    }

    private fun getData(): ArrayList<HomeNavigationModel> {
        return arrayListOf(
            HomeNavigationModel("hao123上网之家", "https://www.hao123.com"),
            HomeNavigationModel("百度一下", "https://www.baidu.com"),
            HomeNavigationModel("凤凰网", "https://i.ifeng.com"),
            HomeNavigationModel("hao123上网之家", "https://www.hao123.com"),
            HomeNavigationModel("hao123上网之家", "https://www.hao123.com"),
            HomeNavigationModel("空白页", ""),
        )
    }
}