package com.hx.app.xinnews.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hx.app.xinnews.constant.Constant;
import com.hx.app.xinnews.fragment.NewsFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> strings;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior, List<String> strings) {
        super(fm, behavior);
        this.strings = strings;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TAB_TITLE, strings.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }


}
