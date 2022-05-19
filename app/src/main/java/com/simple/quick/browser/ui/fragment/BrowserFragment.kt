package com.simple.quick.browser.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.simple.quick.browser.R
import java.io.ByteArrayOutputStream
import java.io.IOException

private const val ARG_PARAM_URL = "arg_param_url"

/**
 * @author YangJ 浏览界面
 */
class BrowserFragment : Fragment() {

    companion object {
        const val TAG = "BrowserFragment"

        @JvmStatic
        fun newInstance(url: String) =
            BrowserFragment().apply {
                arguments = Bundle().apply { putString(ARG_PARAM_URL, url) }
            }
    }

    private var mUrl: String? = null
    private lateinit var mFrameLayout: FrameLayout
    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUrl = it.getString(ARG_PARAM_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_browser, container, false)
        this.mFrameLayout = view.findViewById(R.id.frameLayout)
        if (view is ViewGroup) {
            val webView = createWebView(view.context)
            view.addView(webView)
            this.mWebView = webView
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.mWebView?.let { webView ->
            val parent = webView.parent
            if (parent is ViewGroup) {
                parent.removeView(webView)
            }
            webView.destroy()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView(context: Context): WebView {
        val webView = WebView(context)
        val setting = webView.settings
        setting.cacheMode = WebSettings.LOAD_DEFAULT // 使用缓存的策略，这里使用默认
        setting.allowFileAccess = true // 设置可以访问文件
        setting.databaseEnabled = true
        setting.useWideViewPort = true // 将图片调整到适合WebView的大小
        setting.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW // 开启https和http混用
        setting.blockNetworkImage = false
        setting.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setting.safeBrowsingEnabled = true //
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setting.offscreenPreRaster = false
        }
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        setting.allowContentAccess = true
        setting.displayZoomControls = true
        setting.loadWithOverviewMode = true // 缩放至屏幕的大小
        setting.defaultTextEncodingName = "utf-8" // 设置编码格式
        setting.loadsImagesAutomatically = true // 自动加载图片
        setting.mediaPlaybackRequiresUserGesture = true
        setting.javaScriptCanOpenWindowsAutomatically = true // 支持通过JS弹窗
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                injectJs(view, false)
                Log.i(TAG, "onPageStarted -> url = $url")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.i(TAG, "onLoadResource -> url = $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.i(TAG, "onPageFinished -> url = $url")
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            private var mCustomView: View? = null
            private var mCustomViewCallback: CustomViewCallback? = null
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                Log.i(TAG, "onShowCustomView ->")
                if (mCustomView != null) {
                    callback?.onCustomViewHidden()
                    return
                }
                mCustomView = view
                mFrameLayout.addView(mCustomView)
                mCustomViewCallback = callback
                mWebView?.visibility = View.GONE
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onHideCustomView() {
                mWebView?.visibility = View.VISIBLE
                if (mCustomView == null) {
                    return
                }
                mCustomView?.visibility = View.GONE
                mFrameLayout.removeView(mCustomView)
                mCustomViewCallback!!.onCustomViewHidden()
                mCustomView = null
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                super.onHideCustomView()
                Log.i(TAG, "onHideCustomView ->")
            }
        }
        // 加载页面
        this.mUrl?.let { url ->
            webView.loadUrl(url)
        }
        return webView
    }

    fun getUrl(): String? {
        return this.mUrl
    }

    fun canGoBack(): Boolean {
        val webView = this.mWebView
        val canGoBack = webView?.canGoBack() ?: false
        return if (canGoBack) {
            webView?.goBack()
            true
        } else {
            false
        }
    }

    private fun injectJs(webView: WebView, isDayTheme: Boolean) {
        val resId = if (isDayTheme) {
            R.raw.style_light
        } else {
            R.raw.style_night
        }
        val inputStream = resources.openRawResource(resId)
        var outputStream: ByteArrayOutputStream? = null
        val code: String?
        try {
            outputStream = ByteArrayOutputStream()
            //
            var len: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
            code = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        webView.evaluateJavascript(
            "javascript:(function() {var parent = document.getElementsByTagName('head').item(0);var style = document.createElement('style');style.type = 'text/css';style.innerHTML = window.atob('$code');parent.appendChild(style)})();",
            null
        )
    }
}