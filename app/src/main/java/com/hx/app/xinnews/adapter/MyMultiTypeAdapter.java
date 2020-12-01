package com.hx.app.xinnews.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;
import com.drakeet.multitype.MultiTypeAdapter;
import com.hx.app.xinnews.R;
import com.hx.app.xinnews.bean.Items;

import org.jetbrains.annotations.NotNull;

/**
 * 自定义一个具有加载更多的Adapter
 */
public class MyMultiTypeAdapter extends MultiTypeAdapter {
    //MultiTypeAdapter中有一个方法getItems()可以获取到一个集合，
    // 但是因为这个集合是Kotlin类型的，不能进行转换，所以通过传参数的方式
    private final Items mItems;
    private final OnLoadMoreListener mOnLoadMoreListener;
    private final Context mApplicationContext;
    private RecyclerView mRecycleView;
    private OnMyScrollerListener onMyScrollerListener;

    public MyMultiTypeAdapter(OnLoadMoreListener onLoadMoreListener, Context applicationContext, Items items) {
        mOnLoadMoreListener = onLoadMoreListener;
        mApplicationContext = applicationContext;
        mItems = items;
    }

    /**
     * 关联RecycleView
     */
    public final void attach(RecyclerView recyclerView) {
        mRecycleView = recyclerView;
        LinearLayoutManager manager = (LinearLayoutManager) mRecycleView.getLayoutManager();
        onMyScrollerListener = new OnMyScrollerListener(mItems, manager, this, mOnLoadMoreListener);
        onMyScrollerListener.setLoadingMoreTextItemData(initLoadingMoreNotifyItemData());
        mRecycleView.addOnScrollListener(onMyScrollerListener);
        registerLoadingMoreNotifyItem(LoadingMoreTextItemData.class, new LoadingMoreTextItemView());
    }

    public final void addData(Items items) {
        int tempSize = mItems.size() - 1;
        //移除加载更多
        mItems.remove(tempSize);
        mItems.addAll(items);
        //设置Item
        notifyItemRangeInserted(tempSize, items.size() - 1);
    }

    public final void loadingComplete() {
        onMyScrollerListener.setLoading(false);
    }

    /**
     * 注册加载更多的提示Item
     */
    public final <T> void registerLoadingMoreNotifyItem(Class<T> clazz, ItemViewBinder<T, ?> binder) {
        //注册加载更多的提示Items
        register(clazz, binder);
    }

    /**
     * 用于初始化加载更多提示的Data,子类可以进行重写加入自己的加载提示样式
     */
    public final Object initLoadingMoreNotifyItemData() {
        return new LoadingMoreTextItemData("加载更多...");
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private static class OnMyScrollerListener extends RecyclerView.OnScrollListener {
        private static final int SIZE = 2;
        private final Items mItems;
        private final LinearLayoutManager mLinearLayoutManager;
        private final MyMultiTypeAdapter mAdapter;
        private final OnLoadMoreListener loadMoreListener;
        //记录是否正在加载
        private boolean isLoading = false;
        private Object mLoadingMoreTextItemData;

        public OnMyScrollerListener(Items items, LinearLayoutManager linearLayoutManager, MyMultiTypeAdapter adapter, OnLoadMoreListener loadMoreListener) {
            mItems = items;
            mLinearLayoutManager = linearLayoutManager;
            mAdapter = adapter;
            this.loadMoreListener = loadMoreListener;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            //判断向上向下滑
            if (dy < 0) {
                //向下滑，忽略
                return;
            }
            int totalItems = mLinearLayoutManager.getItemCount();
            int lastVisibleItems = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            //倒数第Size可见的时候开始加载
            if (!isLoading && lastVisibleItems == totalItems - SIZE) {
                //设置正在更新
                isLoading = true;
                //添加正在加载的提示
                if (mLoadingMoreTextItemData != null) {
                    mItems.add(mLoadingMoreTextItemData);
                    mAdapter.notifyItemInserted(mItems.size() - 1);
                }
                loadMoreListener.onLoadMore();

            }
        }


        public void setLoadingMoreTextItemData(Object mLoadingMoreTextItemData) {
            this.mLoadingMoreTextItemData = mLoadingMoreTextItemData;
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }
    }


    private static class LoadingMoreTextItemData {
        private final String message;

        LoadingMoreTextItemData(String message) {
            this.message = message;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.message);
        }
    }

    private class LoadingMoreTextItemView extends ItemViewBinder<LoadingMoreTextItemData, ViewHolder> {

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup viewGroup) {
            LinearLayout linearLayout = new LinearLayout(mApplicationContext);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER);
            TextView textView = new TextView(mApplicationContext);
            textView.setId(R.id.message);
            ViewGroup.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textParams);
            textView.setTextSize(20);
            linearLayout.addView(textView);
            return new ViewHolder(linearLayout);
        }

        @Override
        public void onBindViewHolder(@NotNull ViewHolder viewHolder, LoadingMoreTextItemData loadingMoreTextItemData) {
            viewHolder.textView.setText(loadingMoreTextItemData.message);
        }
    }
}
