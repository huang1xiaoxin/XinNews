package com.hx.app.xinnews.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hx.app.xinnews.constant.TAB_TITLE
import com.hx.app.xinnews.fragment.NewsFragment


class FragmentAdapter(fm: FragmentManager, behavior: Int, private val strings: List<String>) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        val fragment = NewsFragment()
        val bundle = Bundle()
        bundle.putString(TAB_TITLE, strings[position])
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return strings.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return strings[position]
    }
}