package com.hx.app.xinnews.activity

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hx.app.xinnews.R
import com.hx.app.xinnews.adapter.FragmentAdapter
import com.hx.app.xinnews.base.BaseActivity
import com.hx.app.xinnews.constant.NET_ERROR
import com.hx.app.xinnews.databinding.ActivityMainBinding
import com.hx.app.xinnews.viewmodel.MainViewModel
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var mAdapter: FragmentAdapter

    private val list: MutableList<String> = ArrayList()


    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val viewPager: ViewPager = binding.viewPager
        binding.tabLayout.setupWithViewPager(viewPager)
        mAdapter = FragmentAdapter(supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list)
        viewPager.adapter = mAdapter
        //设置缓存的数量
        viewPager.offscreenPageLimit = 4
        tabLayoutSelectedText()
        mainViewModel.getChannelLiveData().observe(this, Observer { data ->
            dismissLoadingDialog()
            //数据访问失败的时候，加载失败的提示
            if (data[0] == NET_ERROR) {
                binding.errorStud.inflate()
                return@Observer
            }
            list.clear()
            list.addAll(data)
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun loadingData() {
        showLoadingDialog()
        mainViewModel.getChannel()
    }

    /**
     * 将选中的字体进行加粗
     */
    private fun tabLayoutSelectedText() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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