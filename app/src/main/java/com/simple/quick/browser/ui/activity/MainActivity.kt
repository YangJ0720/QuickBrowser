package com.simple.quick.browser.ui.activity

import android.view.KeyEvent
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.simple.quick.browser.R
import com.simple.quick.browser.base.BaseActivity
import com.simple.quick.browser.core.TabBrowser
import com.simple.quick.browser.model.HomeNavigationModel
import com.simple.quick.browser.ui.view.HomeBottomNavigationView
import com.simple.quick.browser.ui.view.HomeBrowserContainerView
import com.simple.quick.browser.ui.view.HomeTabBrowserListView

/**
 * @author YangJ 应用程序主界面
 */
class MainActivity : BaseActivity() {

    private lateinit var mHomeBrowserContainerView: HomeBrowserContainerView

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        val browserContainerView = findViewById<HomeBrowserContainerView>(R.id.homeBrowserContainerView)
        this.mHomeBrowserContainerView = browserContainerView
        val bottomNavigationView = findViewById<HomeBottomNavigationView>(R.id.homeBottomNavigationView)
        bottomNavigationView.setCallback(object : HomeBottomNavigationView.Callback {
            override fun switch() {
                showTabBrowserListView(browserContainerView.getTabBrowserList())
            }
        })
    }

    fun onHomeNavigationItemClick(model: HomeNavigationModel) {
        this.mHomeBrowserContainerView.load(model.url)
    }

    private fun showTabBrowserListView(tabBrowserList: List<TabBrowser>) {
        val dialog = BottomSheetDialog(this@MainActivity)
        val listView = HomeTabBrowserListView(this@MainActivity)
        listView.setCallback(object : HomeTabBrowserListView.Callback {
            override fun back() {
                dialog.dismiss()
            }

            override fun add() {
                dialog.dismiss()
                val tabBrowser = TabBrowser(this@MainActivity)
                this@MainActivity.mHomeBrowserContainerView.addTabBrowser(tabBrowser)
            }

            override fun clear() {
                dialog.dismiss()
            }
        })
        listView.setData(tabBrowserList)
        dialog.setContentView(listView)
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断网页是否可以回退
            if (this.mHomeBrowserContainerView.goBack()) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}