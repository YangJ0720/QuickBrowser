package com.simple.quick.browser.core

interface TabBrowserCallback {

    fun load(url: String)

    fun goBack(): Boolean
}