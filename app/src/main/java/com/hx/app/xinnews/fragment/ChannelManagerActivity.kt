package com.hx.app.xinnews.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hx.app.xinnews.adapter.GrideViewAdapter
import com.hx.app.xinnews.base.BaseActivity
import com.hx.app.xinnews.databinding.ChannelManageFragmentBinding
import com.hx.app.xinnews.ui.constraint.CustomLayer
import com.hx.app.xinnews.viewmodel.MainViewModel

class ChannelManagerActivity : BaseActivity() {
    private lateinit var mBinding: ChannelManageFragmentBinding

    private val lists:MutableList<String> = mutableListOf("新闻","头条","军事","国际","时尚","北京")
    /**
     * 让子类去实现初始化一个View
     *
     * @param savedInstanceState
     * @return
     */
    override fun initView(savedInstanceState: Bundle?) {
        mBinding = ChannelManageFragmentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val adapter =GrideViewAdapter(lists,this)
        mBinding.myChannelGridView.adapter=adapter
    }

    /**
     * 加载数据
     */
    override fun loadingData() {

    }

}