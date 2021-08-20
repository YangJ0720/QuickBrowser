package com.simple.quick.browser.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.simple.quick.browser.R
import com.simple.quick.browser.ui.fragment.BrowserFragment
import com.simple.quick.browser.ui.fragment.HomeFragment

class TabBrowser: FrameLayout, TabBrowserCallback {

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
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_tab_browser, this)
        // 显示主页
        val manager = (context as AppCompatActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.frameLayout, HomeFragment.newInstance(), HomeFragment.TAG)
        transaction.commit()
    }

    fun getUrl(): String? {
        val manager = (context as AppCompatActivity).supportFragmentManager
        val fragment = manager.findFragmentByTag(BrowserFragment.TAG)
        return if (fragment is BrowserFragment) {
            fragment.getUrl()
        } else {
            "Home"
        }
    }

    override fun load(url: String) {
        val manager = (context as AppCompatActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        val tag = BrowserFragment.TAG
        transaction.add(R.id.frameLayout, BrowserFragment.newInstance(url), tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    override fun goBack(): Boolean {
        val manager = (context as AppCompatActivity).supportFragmentManager
        val fragment = manager.findFragmentByTag(BrowserFragment.TAG)
        if (fragment is BrowserFragment) {
            return fragment.canGoBack()
        }
        return false
    }
}