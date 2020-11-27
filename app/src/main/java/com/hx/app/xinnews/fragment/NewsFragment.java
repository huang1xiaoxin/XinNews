package com.hx.app.xinnews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hx.app.xinnews.R;
import com.hx.app.xinnews.base.BaseFragment;
import com.hx.app.xinnews.constant.Constant;
import com.hx.app.xinnews.databinding.NewsListFagmentBinding;
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData;
import com.hx.app.xinnews.mutitype.itemview.NewsListItemView;
import com.hx.app.xinnews.viewmodel.MainViewModel;


public class NewsFragment extends BaseFragment<MainViewModel> {

    private NewsListFagmentBinding mBinding;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding=NewsListFagmentBinding.inflate(inflater,container,false);
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(context));
        mBinding.recycleView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        return mBinding.getRoot();
    }

    @Override
    protected void registerItems() {
        super.registerItems();
        mAdapter.register(NewsListItemData.class,new NewsListItemView(context));
        setRecycleViewAdapter(mBinding.recycleView);
    }

    @Override
    protected void loadingData() {
        showLoadingDialog();
        Bundle bundle=getArguments();
        assert bundle != null;
        String channel=bundle.getString(Constant.TAB_TITLE,"头条");
        mViewModel.getNewsTop20(channel);
    }

    @Override
    protected void registerLiveDataObserver() {
        mViewModel.getNewsListLiveData().observe(this,newsListItemData -> {
            items.addAll(newsListItemData);
            mAdapter.notifyDataSetChanged();
            dismissLoadingDialog();
        });
    }

    @Override
    protected MainViewModel initViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }


}
