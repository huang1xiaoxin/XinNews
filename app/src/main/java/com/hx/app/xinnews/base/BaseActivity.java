package com.hx.app.xinnews.base;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hx.app.xinnews.ui.LoadingDialog;

public abstract class BaseActivity extends AppCompatActivity {
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog= new LoadingDialog.Builder(this).setCancelable(true).setMessage("" +
                "正在加载...").setCancelOutside(true).setShowMessage(true).create();
        initView(savedInstanceState);
        loadingData();
    }

    /**
     *  让子类去实现初始化一个View
     * @param savedInstanceState
     * @return
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);


    /**
     * 加载数据
     */
    protected abstract void loadingData();

    /**
     * 显示对话框
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }



}
