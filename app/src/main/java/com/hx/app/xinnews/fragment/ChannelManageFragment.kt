package com.hx.app.xinnews.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hx.app.xinnews.adapter.GrideViewAdapter
import com.hx.app.xinnews.base.BaseFragment
import com.hx.app.xinnews.bean.SharedPreferencesUtil
import com.hx.app.xinnews.constant.HOT_CHANNEL_KEY
import com.hx.app.xinnews.constant.MY_CHANNEL_KEY
import com.hx.app.xinnews.databinding.ChannelManageFragmentBinding
import com.hx.app.xinnews.ui.constraint.CustomLayer
import com.hx.app.xinnews.viewmodel.MainViewModel
import kotlin.math.log

class ChannelManageFragment : BaseFragment() {

    private lateinit var mBinding: ChannelManageFragmentBinding



    private val myChannelList: MutableList<String> = mutableListOf()

    private val hotChannelList: MutableList<String> = mutableListOf()


    /**
     * 让子类去实现初始化一个View
     *
     * @param savedInstanceState
     * @return
     */
    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View{
        mBinding = ChannelManageFragmentBinding.inflate(layoutInflater)
        val myChannelAdapter = context?.let {
            GrideViewAdapter(myChannelList, it, CustomLayer.NULL_TEXT)
        }
        val hotChannelAdapter = context?.let {
            GrideViewAdapter(hotChannelList, it, CustomLayer.PLUS_TEXT)
        }
        mBinding.myChannelGridView.adapter = myChannelAdapter
        mBinding.hotChannelGridView.adapter = hotChannelAdapter
        mViewModel.mMyChannelLiveData.observe(this) {
            myChannelList.clear()
            myChannelList.addAll(it)
            myChannelAdapter?.notifyDataSetChanged()
            dismissLoadingDialog()
        }
        mViewModel.mHotChannelLiveData.observe(this) {
            hotChannelList.clear()
            hotChannelList.addAll(it)
            hotChannelAdapter?.notifyDataSetChanged()
        }
        mBinding.editor.setOnClickListener {
            if (mBinding.editor.text == """完成""") {
                myChannelAdapter?.tag = CustomLayer.NULL_TEXT
                myChannelAdapter?.notifyDataSetChanged()
                mBinding.editor.text = """编辑"""
            } else {
                myChannelAdapter?.tag = CustomLayer.REDUCE_TEXT
                myChannelAdapter?.notifyDataSetChanged()
                mBinding.editor.text = """完成"""
            }

        }
        mBinding.myChannelGridView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            if (mBinding.editor.text == """完成""") {
                val removeTemp = myChannelList[i]
                myChannelList.removeAt(i)
                hotChannelList.add(removeTemp)
                hotChannelAdapter?.notifyDataSetChanged()
                myChannelAdapter?.notifyDataSetChanged()
                saveEditChannel()
            }
        }
        mBinding.hotChannelGridView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val removeTemp = hotChannelList[i]
            hotChannelList.removeAt(i)
            myChannelList.add(removeTemp)
            hotChannelAdapter?.notifyDataSetChanged()
            myChannelAdapter?.notifyDataSetChanged()
            saveEditChannel()
        }
        return mBinding.root
    }

    override fun registerLiveDataObserver() {
        super.registerLiveDataObserver()
    }

    /**
     * 加载数据
     */
    override fun loadingData() {
        showLoadingDialog()
        context?.let {
            mViewModel.getMyChannel(it, MY_CHANNEL_KEY, "头条")
            mViewModel.getHotChannel(it, HOT_CHANNEL_KEY, "娱乐")
        }
    }

    private fun saveEditChannel() {
        //存取值到SharedPreferences
        val myChannelBuilder = StringBuilder()
        myChannelList.forEach {
            myChannelBuilder.append(" ").append(it)
        }
        val hotChannelBuilder = StringBuilder()
        hotChannelList.forEach {
            hotChannelBuilder.append(" ").append(it)
        }
        context?.let {
            SharedPreferencesUtil(it).putValue(MY_CHANNEL_KEY, myChannelBuilder.toString())
            SharedPreferencesUtil(it).putValue(HOT_CHANNEL_KEY, hotChannelBuilder.toString())
        }
    }


}