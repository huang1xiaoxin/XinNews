package com.hx.app.xinnews.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hx.app.xinnews.base.BaseFragment
import com.hx.app.xinnews.databinding.ChannelManageFragmentBinding
import com.hx.app.xinnews.ui.constraint.CustomLayer
import com.hx.app.xinnews.viewmodel.MainViewModel

@Deprecated("用ChannelManagerActivity代替")
class ChannelManageFragment : BaseFragment<MainViewModel>() {

    private lateinit var mBinding: ChannelManageFragmentBinding

    /**
     * 让子类去实现初始化一个View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = ChannelManageFragmentBinding.inflate(layoutInflater)
        return mBinding.root
    }

    /**
     * 加载数据
     */
    override fun loadingData() {

    }
}