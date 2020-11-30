package com.hx.app.xinnews.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hx.app.xinnews.base.BaseActivity;
import com.hx.app.xinnews.databinding.NewsContextLayoutBinding;

public class NewsContextActivity extends BaseActivity {
    private NewsContextLayoutBinding mBing;

    @Override
    protected View initView(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        mBing=NewsContextLayoutBinding.inflate(getLayoutInflater());
        return mBing.getRoot();
    }


    @Override
    protected void loadingData() {

    }

}
