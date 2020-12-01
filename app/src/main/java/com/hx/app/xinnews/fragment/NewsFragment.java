package com.hx.app.xinnews.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hx.app.xinnews.activity.NewsContentActivity;
import com.hx.app.xinnews.adapter.MyMultiTypeAdapter;
import com.hx.app.xinnews.base.BaseFragment;
import com.hx.app.xinnews.bean.Items;
import com.hx.app.xinnews.constant.Constant;
import com.hx.app.xinnews.databinding.NewsListFagmentBinding;
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData;
import com.hx.app.xinnews.mutitype.itemview.NewsListItemView;
import com.hx.app.xinnews.myinterface.OnRecycleViewItemClickListener;
import com.hx.app.xinnews.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;


public class NewsFragment extends BaseFragment<MainViewModel>  {
    //每次加载更多的时候加载10条
    private final static int NUM=10;
    private int mStartIndex=20;
    private NewsListFagmentBinding mBinding;
    private String mChannel;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding=NewsListFagmentBinding.inflate(inflater,container,false);
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(context));
        mBinding.recycleView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mAdapter.attach(mBinding.recycleView);
        return mBinding.getRoot();
    }

    @Override
    protected void registerItems() {
        super.registerItems();
        mAdapter.register(NewsListItemData.class,new NewsListItemView(context,new OnRecycleViewItemClickListener(){

            @Override
            public void onItemClick(@Nullable View view, @NotNull NewsListItemData data) {
                Intent intent=new Intent(context, NewsContentActivity.class);
                intent.putExtra(Constant.CONTENT,data.getContent());
                intent.putExtra(Constant.TITLE,data.getTitle());
                startActivity(intent);
            }
        }));
        setRecycleViewAdapter(mBinding.recycleView);
    }

    @Override
    protected void loadingData() {
        showLoadingDialog();
        Bundle bundle=getArguments();
        assert bundle != null;
        mChannel=bundle.getString(Constant.TAB_TITLE,"头条");
        mViewModel.getNewsTop20(mChannel);
    }

    @Override
    protected void registerLiveDataObserver() {
        mViewModel.getNewsListLiveData().observe(this,newsListItemData -> {
            items.addAll(newsListItemData);
            mAdapter.notifyDataSetChanged();
            dismissLoadingDialog();
        });

        mViewModel.getLoadingMoreNewsLiveData().observe(this,newsListItemData ->{
            Items items=new Items();
            items.addAll(newsListItemData);
            mAdapter.addData(items);
            mAdapter.loadingComplete();
        });
    }

    @Override
    protected MainViewModel initViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }


    @Override
    public void onLoadMore() {
        mViewModel.getMoreNews(mChannel,mStartIndex,NUM);
        mStartIndex+=NUM;
    }
}
