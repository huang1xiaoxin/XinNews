package com.hx.app.xinnews.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hx.app.xinnews.R
import com.hx.app.xinnews.bean.SharedPreferencesUtil
import com.hx.app.xinnews.constant.FIRST_OPEN_KEY
import com.hx.app.xinnews.constant.HOT_CHANNEL_KEY
import com.hx.app.xinnews.constant.MY_CHANNEL_KEY
import com.hx.app.xinnews.viewmodel.MainViewModel
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
