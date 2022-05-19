package com.simple.quick.browser.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.simple.quick.browser.core.TabBrowser
import com.simple.quick.browser.core.TabBrowserCallback

class HomeBrowserContainerView : FrameLayout, TabBrowserCallback {

    private var mIndex: Int = 0
    private var mList: ArrayList<TabBrowser> = arrayListOf()
    private var mTabBrowser: TabBrowser? = null

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
        val tabBrowser = TabBrowser(context)
        setTabBrowser(tabBrowser)
        this.mList.add(tabBrowser)
    }

    private fun setTabBrowser(tabBrowser: TabBrowser) {
        if (childCount > 0) {
            removeAllViews()
        }
        addView(tabBrowser)
        this.mTabBrowser = tabBrowser
    }

    fun addTabBrowser(tabBrowser: TabBrowser) {
        this.mIndex++
        this.mList.add(tabBrowser)
        setTabBrowser(tabBrowser)
    }

    fun clearTabBrowser() {
        this.mIndex = 0
        addTabBrowser(TabBrowser(context))
    }

    fun getTabBrowserList(): List<TabBrowser> {
        return this.mList
    }

    override fun load(url: String) {
        this.mTabBrowser?.load(url)
    }

    override fun goBack(): Boolean {
        return this.mTabBrowser?.goBack() ?: false
    }
}