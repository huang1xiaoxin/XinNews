package com.hx.app.xinnews.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hx.app.xinnews.R
import com.hx.app.xinnews.adapter.FragmentAdapter
import com.hx.app.xinnews.base.BaseFragment
import com.hx.app.xinnews.constant.NET_ERROR
import com.hx.app.xinnews.databinding.FragmentMainBinding
import com.hx.app.xinnews.viewmodel.MainViewModel
import java.util.*

class MainFragment : BaseFragment<MainViewModel>() {
    private lateinit var mBinding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel


    private lateinit var mFragmentAdapter: FragmentAdapter

    private val list: MutableList<String> = ArrayList()


    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val viewPager: ViewPager = mBinding.viewPager
        mBinding.tabLayout.setupWithViewPager(viewPager)
        //一定要用childFragmentManager,不然继续打开Fragment返回数据会空白
        mFragmentAdapter = FragmentAdapter(parentFragmentManager,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list)
        viewPager.adapter = mFragmentAdapter
        //设置缓存的数量
        viewPager.offscreenPageLimit = 4
        tabLayoutSelectedText()
        mainViewModel.getChannelLiveData().observe(this, Observer { data ->
            dismissLoadingDialog()
            //数据访问失败的时候，加载失败的提示
            if (data[0] == NET_ERROR) {
                mBinding.errorStud.inflate()
                return@Observer
            }
            list.clear()
            list.addAll(data)
            mFragmentAdapter.notifyDataSetChanged()
        })
        mBinding.imageButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_channelManagerActivity)
        }
        return mBinding.root
    }

    override fun loadingData() {
        showLoadingDialog()
        mainViewModel.getChannel()
    }

    /**
     * 将选中的字体进行加粗
     */
    private fun tabLayoutSelectedText() {
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                if (p0!!.customView == null) {
                    p0.setCustomView(R.layout.tab_layout_text)
                }
                //根据源码知道id一定是android.R.id.text1
                val textView = p0.customView!!.findViewById<TextView>(android.R.id.text1)
                textView.setTextAppearance(R.style.TabLayoutUnTextSelected)
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.customView == null) {
                    p0?.setCustomView(R.layout.tab_layout_text)
                }
                //根据源码知道id一定是android.R.id.text1
                val textView = p0?.customView!!.findViewById<TextView>(android.R.id.text1)
                textView.setTextAppearance(R.style.TabLayoutTextSelected)
            }

        })
    }
}