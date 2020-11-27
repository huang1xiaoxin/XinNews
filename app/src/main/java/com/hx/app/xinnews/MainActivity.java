package com.hx.app.xinnews;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.hx.app.xinnews.adapter.FragmentAdapter;
import com.hx.app.xinnews.databinding.ActivityMainBinding;
import com.hx.app.xinnews.ui.LoadingDialog;
import com.hx.app.xinnews.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadingDialog mLoadingDialog;

    private MainViewModel mainViewModel;

    private ActivityMainBinding binding;

    private  FragmentAdapter mAdapter;

    private List<String> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        initView();
        initData();
    }

    private void initView() {
        setContentView(binding.getRoot());
        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);
        ViewPager viewPager=binding.viewPager;
        binding.tableLayout.setupWithViewPager(viewPager);
        mAdapter=new FragmentAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,list);
        viewPager.setAdapter(mAdapter);
        //设置缓存的数量
        viewPager.setOffscreenPageLimit(4);
        tabLayoutSelectedText();
        mLoadingDialog= new LoadingDialog.Builder(this).setCancelable(true).setMessage("" +
                "正在加载...").setCancelOutside(true).setShowMessage(true).create();
        mainViewModel.getChannelLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                list.clear();
                list.addAll(strings);
                mAdapter.notifyDataSetChanged();
                mLoadingDialog.dismiss();
            }
        });

    }

    public void initData(){
        mLoadingDialog.show();
        mainViewModel.getChannel();
    }



    /**
     * 将选中的字体进行加粗
     */
    public void tabLayoutSelectedText(){
        binding.tableLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView()==null){
                    tab.setCustomView(R.layout.tab_layout_text);
                }
                //根据源码知道id一定是android.R.id.text1
                TextView textView=tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(R.style.TabLayoutTextSelected);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView()==null){
                    tab.setCustomView(R.layout.tab_layout_text);
                }
                //根据源码知道id一定是android.R.id.text1
                TextView textView=tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(R.style.TabLayoutUnTextSelected);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * 显示对话框
     */
    public void showLoadingDialog(){
        if (mLoadingDialog!=null){
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    public void dismissLoadingDialog(){
        if (mLoadingDialog!=null){
            mLoadingDialog.dismiss();
        }
    }
}