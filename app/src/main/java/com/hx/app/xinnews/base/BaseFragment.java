package com.hx.app.xinnews.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.MultiTypeAdapter;
import com.hx.app.xinnews.bean.Items;
import com.hx.app.xinnews.ui.LoadingDialog;

public abstract class BaseFragment<T extends ViewModel> extends Fragment {
    private LoadingDialog mLoadingDialog;

    public Context context;

    private boolean isFist=true;

    public T mViewModel;

    public final MultiTypeAdapter mAdapter = new MultiTypeAdapter();
    ;

    /**
     * 设置给MutableAdapter设置的集合
     */
    public final Items items = new Items();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new LoadingDialog.Builder(getActivity()).setCancelable(true).setMessage("" +
                "正在加载...").setCancelOutside(true).setShowMessage(true).create();
        mViewModel = initViewModel();
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        registerItems();
        registerLiveDataObserver();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFist){
            loadingData();
            isFist=false;
        }

    }

    /**
     * 用于注册MultiType的Items
     */
    protected void registerItems() {

    }

    /**
     * 用来给子类设置RecycleView的Items
     */
    protected void setRecycleViewAdapter(RecyclerView recyclerView) {
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItems(items);
    }

    /**
     * 要求子类去实现，用来初始化ViewModel
     *
     * @return
     */
    protected abstract T initViewModel();


    /**
     * 让子类去实现初始化一个View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    /**
     * 加载数据
     */
    protected abstract void loadingData();


    /**
     * 显示对话框
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 用于注册LiveData的观测者
     */
    protected abstract void registerLiveDataObserver();


}
