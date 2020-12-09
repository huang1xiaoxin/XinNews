package com.hx.app.xinnews.activity

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hx.app.xinnews.R
import com.hx.app.xinnews.base.BaseActivity
import com.hx.app.xinnews.constant.CONTENT
import com.hx.app.xinnews.constant.TITLE
import com.hx.app.xinnews.databinding.NewsContextLayoutBinding

class NewsContentActivity : BaseActivity() {

    private lateinit var mBing: NewsContextLayoutBinding

    //使用ApplicationContext防止内存泄露
    private lateinit var mWebView: WebView

    companion object{
        const val  TAG="""NewsContentActivity"""
    }


    override fun initView(savedInstanceState: Bundle?) {
        mBing = NewsContextLayoutBinding.inflate(layoutInflater)
        setContentView(mBing.root)
        mWebView = WebView(applicationContext)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        mBing.linearLayout.addView(mWebView, layoutParams)
        showLoadingDialog()

        mWebView.settings.apply {
            javaScriptEnabled = true //支持JS
            useWideViewPort = true //将图片调整到合适的webView的大小
            loadWithOverviewMode = true //缩放至屏幕的大小
            //缩放操作
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //隐藏原生的缩放控件
            //设置字体的大小
            textZoom = resources.getDimensionPixelOffset(R.dimen.content_title) * 4
        }

        mWebView.webViewClient = object : WebViewClient() {
            //数据加载完成
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                imgReset()
                dismissLoadingDialog()
                Log.e(TAG, "onPageFinished: 网页内容加载完成" )
            }
        }
    }

    override fun loadingData() {
        val content = intent.getStringExtra(CONTENT)
        val title = intent.getStringExtra(TITLE)
        //加载数据
        content?.let{
            mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
            Log.e(TAG, "loadingData: 加载Html数据$it")
            mBing.title.text = title
        }

    }

    override fun onDestroy() {
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        //清楚历史记录
        mWebView.clearHistory()
        (mWebView.parent as ViewGroup).removeView(mWebView)
        mWebView.destroy()
        super.onDestroy()
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     * ps:图片可能会存在压缩的问题
     */
    private fun imgReset() {
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()")
    }
}
