package com.hx.app.xinnews.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hx.app.xinnews.base.BaseActivity;
import com.hx.app.xinnews.constant.Constant;
import com.hx.app.xinnews.databinding.NewsContextLayoutBinding;
import com.hx.app.xinnews.viewmodel.MainViewModel;

public class NewsContentActivity extends BaseActivity {
    private NewsContextLayoutBinding mBing;
    
    private WebView mWebView;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mBing=NewsContextLayoutBinding.inflate(getLayoutInflater());
        setContentView(mBing.getRoot());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //使用ApplicationContext防止内存泄露
        mWebView=new WebView(getApplicationContext());
        mBing.getRoot().addView(mWebView,layoutParams);
        showLoadingDialog();
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);//将图片调整到合适的webView的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        mWebView.setWebViewClient(new WebViewClient(){
            //数据加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissLoadingDialog();
            }
        });
    }


    @Override
    protected void loadingData() {
        String content=getIntent().getStringExtra(Constant.CONTENT);
        assert content!=null;
        //加载数据
        mWebView.loadDataWithBaseURL(null,content,"text/html","utf-8",null);

    }

    @Override
    protected void onDestroy() {
        if (mWebView!=null){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            //清楚历史记录
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDestroy();
    }
}