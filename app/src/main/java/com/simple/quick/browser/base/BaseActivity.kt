package com.simple.quick.browser.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResID())
        initData()
        initView()
    }

    @SuppressLint("ResourceType")
    abstract fun getLayoutResID(): Int

    abstract fun initData()

    abstract fun initView()
}