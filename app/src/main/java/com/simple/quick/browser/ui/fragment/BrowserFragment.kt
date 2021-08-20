package com.simple.quick.browser.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.simple.quick.browser.R

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
        WebView.enableSlowWholeDocumentDraw()
        val webView = WebView(context)
        val setting = webView.settings
        setting.allowFileAccess = true
        setting.blockNetworkImage = false
        setting.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }
        }
        webView.webChromeClient = object : WebChromeClient() {

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
}