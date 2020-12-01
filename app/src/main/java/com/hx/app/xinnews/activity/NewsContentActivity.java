package com.hx.app.xinnews.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.hx.app.xinnews.R;
import com.hx.app.xinnews.base.BaseActivity;
import com.hx.app.xinnews.constant.Constant;
import com.hx.app.xinnews.databinding.NewsContextLayoutBinding;

public class NewsContentActivity extends BaseActivity {
    private NewsContextLayoutBinding mBing;

    private WebView mWebView;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mBing = NewsContextLayoutBinding.inflate(getLayoutInflater());
        setContentView(mBing.getRoot());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //使用ApplicationContext防止内存泄露
        mWebView = new WebView(getApplicationContext());
        mBing.linearLayout.addView(mWebView, layoutParams);
        showLoadingDialog();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        settings.setUseWideViewPort(true);//将图片调整到合适的webView的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //设置字体的大小
        settings.setTextZoom(getResources().getDimensionPixelOffset(R.dimen.content_title) * 4);
        mWebView.setWebViewClient(new WebViewClient() {
            //数据加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
                dismissLoadingDialog();
            }
        });


    }


    @Override
    protected void loadingData() {
        String content = getIntent().getStringExtra(Constant.CONTENT);
        String title = getIntent().getStringExtra(Constant.TITLE);
        assert content != null;
        //加载数据
        mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        mBing.title.setText(title);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清楚历史记录
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     * ps:图片可能会存在压缩的问题
     **/
    private void imgReset() {
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

}
