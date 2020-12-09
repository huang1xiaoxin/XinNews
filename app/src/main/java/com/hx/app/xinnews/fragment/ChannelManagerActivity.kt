package com.hx.app.xinnews.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hx.app.xinnews.adapter.GrideViewAdapter
import com.hx.app.xinnews.base.BaseActivity
import com.hx.app.xinnews.bean.SharedPreferencesUtil
import com.hx.app.xinnews.constant.HOT_CHANNEL_KEY
import com.hx.app.xinnews.constant.MY_CHANNEL_KEY
import com.hx.app.xinnews.databinding.ChannelManageFragmentBinding
import com.hx.app.xinnews.ui.constraint.CustomLayer
import com.hx.app.xinnews.viewmodel.MainViewModel
import okhttp3.internal.notify
import java.lang.StringBuilder

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
            if (mBinding.editor.text=="""完成"""){
                myChannelAdapter.tag=CustomLayer.NULL_TEXT
                myChannelAdapter.notifyDataSetChanged()
                mBinding.editor.text="""编辑"""
            }else{
                myChannelAdapter.tag=CustomLayer.REDUCE_TEXT
                myChannelAdapter.notifyDataSetChanged()
                mBinding.editor.text="""完成"""
            }

        }
        mBinding.myChannelGridView.setOnItemClickListener {
            adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (mBinding.editor.text=="""完成"""){
                val removeTemp=myChannelList[i]
                myChannelList.removeAt(i)
                hotChannelList.add(removeTemp)
                hotChannelAdapter.notifyDataSetChanged()
                myChannelAdapter.notifyDataSetChanged()
            }
        }
        mBinding.hotChannelGridView.setOnItemClickListener {
            adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                val removeTemp=hotChannelList[i]
                hotChannelList.removeAt(i)
                myChannelList.add(removeTemp)
                hotChannelAdapter.notifyDataSetChanged()
                myChannelAdapter.notifyDataSetChanged()
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

    override fun onPause() {
        super.onPause()
        //存取值到SharedPreferences
        val myChannelBuilder=StringBuilder()
        myChannelList.forEach {
            myChannelBuilder.append(" ").append(it)
        }
        SharedPreferencesUtil(this).putValue(MY_CHANNEL_KEY,myChannelBuilder.toString())
        val hotChannelBuilder=StringBuilder()
        hotChannelList.forEach {
            hotChannelBuilder.append(" ").append(it)
        }
        SharedPreferencesUtil(this).putValue(HOT_CHANNEL_KEY,hotChannelBuilder.toString())
    }

}