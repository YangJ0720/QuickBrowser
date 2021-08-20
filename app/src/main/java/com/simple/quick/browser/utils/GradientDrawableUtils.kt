package com.simple.quick.browser.utils

import android.graphics.drawable.GradientDrawable
import androidx.annotation.DrawableRes

class GradientDrawableUtils {

    companion object {

        fun factory(@DrawableRes color: Int, radius: Float): GradientDrawable {
            return GradientDrawable().apply {
                setColor(color)
                cornerRadius = radius
            }
        }
    }
}