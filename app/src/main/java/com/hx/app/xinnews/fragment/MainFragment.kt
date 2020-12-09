package com.hx.app.xinnews.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hx.app.xinnews.R
import com.hx.app.xinnews.adapter.FragmentAdapter
import com.hx.app.xinnews.base.BaseFragment
import com.hx.app.xinnews.bean.SharedPreferencesUtil
import com.hx.app.xinnews.constant.FIRST_OPEN_KEY
import com.hx.app.xinnews.constant.HOT_CHANNEL_KEY
import com.hx.app.xinnews.constant.MY_CHANNEL_KEY
import com.hx.app.xinnews.constant.NET_ERROR
import com.hx.app.xinnews.databinding.FragmentMainBinding
import java.util.*

class MainFragment : BaseFragment() {

    private val TAG: String = """"MainFragment"""

    private lateinit var mBinding: FragmentMainBinding


    private lateinit var mFragmentAdapter: FragmentAdapter

    private val list: MutableList<String> = ArrayList()

    private var firstOpen: Boolean = true


    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentMainBinding.inflate(layoutInflater)
        val viewPager: ViewPager = mBinding.viewPager
        mBinding.tabLayout.setupWithViewPager(viewPager)
        mFragmentAdapter = FragmentAdapter(childFragmentManager,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list)
        viewPager.adapter = mFragmentAdapter
        //设置缓存的数量
        viewPager.offscreenPageLimit = 4
        tabLayoutSelectedText()

        activity?.let {
            SharedPreferencesUtil(it).putValue(FIRST_OPEN_KEY, true)
            val tempFirstOpen: Boolean = SharedPreferencesUtil(it).findValue(FIRST_OPEN_KEY, true)
            firstOpen = tempFirstOpen
        }
        if (firstOpen) {
            mViewModel.getChannel()
            mViewModel.mChannels.observe(this) {
                val myChannelBuilder = StringBuilder()
                val hotChannelBuilder = StringBuilder()
                for (i in it.indices) {
                    if (i < 4) {
                        myChannelBuilder.append(" ").append(it[i])
                    } else {
                        hotChannelBuilder.append(" ").append(it[i])
                    }
                }
                val myChannel = myChannelBuilder.toString()
                val hotChannel = hotChannelBuilder.toString()
                activity?.let { con ->
                    SharedPreferencesUtil(con).putValue(MY_CHANNEL_KEY, myChannel)
                    SharedPreferencesUtil(con).putValue(HOT_CHANNEL_KEY, hotChannel)
                    mViewModel.getMyChannel(con, MY_CHANNEL_KEY, "头条")
                    SharedPreferencesUtil(con).putValue(FIRST_OPEN_KEY, false)
                }
            }

        }


        mViewModel.mMyChannelLiveData.observe(this, Observer { data ->
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


        mBinding.imageView.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_channelManagerActivity)
        }
        return mBinding.root
    }

    override fun loadingData() {
        showLoadingDialog()
        if (!firstOpen) {
            context?.let {
                mViewModel.getMyChannel(it, MY_CHANNEL_KEY, "头条")
            }
        }

    }

    /**
     * 将选中的字体进行加粗
     */
    private fun tabLayoutSelectedText() {
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.let {
                    it.customView?.let { view ->
                        { //根据源码知道id一定是android.R.id.text1
                            val textView = view.findViewById<TextView>(android.R.id.text1)
                            textView?.setTextAppearance(R.style.TabLayoutUnTextSelected)
                        }
                    } ?: it.setCustomView(R.layout.tab_layout_text)

                }

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    it.customView?.let { view ->
                        //根据源码知道id一定是android.R.id.text1
                        val textView = view.findViewById<TextView>(android.R.id.text1)
                        textView.setTextAppearance(R.style.TabLayoutTextSelected)
                    } ?: it.setCustomView(R.layout.tab_layout_text)

                }
            }

        })
    }


}