package com.hx.app.xinnews.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hx.app.xinnews.adapter.GrideViewAdapter
import com.hx.app.xinnews.base.BaseActivity
import com.hx.app.xinnews.constant.HOT_CHANNEL_KEY
import com.hx.app.xinnews.constant.MY_CHANNEL_KEY
import com.hx.app.xinnews.databinding.ChannelManageFragmentBinding
import com.hx.app.xinnews.ui.constraint.CustomLayer
import com.hx.app.xinnews.viewmodel.MainViewModel

class ChannelManagerActivity : BaseActivity() {
    private lateinit var mBinding: ChannelManageFragmentBinding

    private lateinit var mViewModel: MainViewModel

    private val myChannelList: MutableList<String> = mutableListOf()
    private val hotChannelList: MutableList<String> = mutableListOf()


    /**
     * 让子类去实现初始化一个View
     *
     * @param savedInstanceState
     * @return
     */
    override fun initView(savedInstanceState: Bundle?) {
        mBinding = ChannelManageFragmentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val myChannelAdapter = GrideViewAdapter(myChannelList, this,CustomLayer.NULL_TEXT)
        val hotChannelAdapter = GrideViewAdapter(hotChannelList, this,CustomLayer.PLUS_TEXT)
        mBinding.myChannelGridView.adapter = myChannelAdapter
        mBinding.hotChannelGridView.adapter = hotChannelAdapter
        mViewModel.mMyChannelLiveData.observe(this) {
            myChannelList.clear()
            myChannelList.addAll(it)
            myChannelAdapter.notifyDataSetChanged()
            dismissLoadingDialog()
        }
        mViewModel.mHotChannelLiveData.observe(this) {
            hotChannelList.clear()
            hotChannelList.addAll(it)
            hotChannelAdapter.notifyDataSetChanged()
        }
        mBinding.editor.setOnClickListener{

        }

    }

    /**
     * 加载数据
     */
    override fun loadingData() {
        showLoadingDialog()
        mViewModel.getMyChannel(this, MY_CHANNEL_KEY, "头条")
        mViewModel.getHotChannel(this, HOT_CHANNEL_KEY, "娱乐")
    }

}