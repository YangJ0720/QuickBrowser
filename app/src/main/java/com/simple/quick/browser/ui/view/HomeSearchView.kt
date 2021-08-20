package com.simple.quick.browser.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.simple.quick.browser.R
import com.simple.quick.browser.utils.GradientDrawableUtils

/**
 * @author YangJ 首页搜索框
 */
class HomeSearchView : LinearLayout {

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
        background = GradientDrawableUtils.factory(android.R.color.holo_red_light, 10.0f)
        //
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_home_search, this)
    }
}