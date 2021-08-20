package com.simple.quick.browser.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.simple.quick.browser.R

class HomeBottomNavigationView: LinearLayout {

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
        orientation = HORIZONTAL
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_home_bottom_navigation, this)
        view.findViewById<View>(R.id.tv_home_bottom_navigation_switch).setOnClickListener {
            this.mCallback?.switch()
        }
    }

    fun setCallback(callback: Callback) {
        this.mCallback = callback
    }

    interface Callback {
        fun switch()
    }
}