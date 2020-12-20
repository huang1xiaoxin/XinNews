package com.hx.app.xinnews.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.hx.app.xinnews.adapter.MyMultiTypeAdapter
import com.hx.app.xinnews.adapter.MyMultiTypeAdapter.OnLoadMoreListener
import com.hx.app.xinnews.bean.Items
import com.hx.app.xinnews.ui.LoadingDialog
import com.hx.app.xinnews.viewmodel.MainViewModel

abstract class BaseFragment : Fragment(), OnLoadMoreListener {
    /**
     * 设置给MutableAdapter设置的集合
     */
    val items = Items()
    val mViewModel:MainViewModel by viewModels()

    lateinit var mAdapter: MyMultiTypeAdapter
    private var mLoadingDialog: LoadingDialog? = null
    private var isFist = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         context?.let {
            mLoadingDialog = LoadingDialog.Builder(it).setCancelable(true).setMessage("" +
                    "正在加载...").setCancelOutside(true).setShowMessage(true).create()

            mAdapter = MyMultiTypeAdapter(this, activity?.applicationContext, items)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = initView(inflater, container, savedInstanceState)
        registerItems()
        registerLiveDataObserver()
        return view
    }

    override fun onResume() {
        super.onResume()
        if (isFist) {
            showLoadingDialog()
            isFist = false
        }
        loadingData()

    }

    /**
     * 用于注册MultiType的Items
     */
    protected open fun registerItems() {}

    /**
     * 用来给子类设置RecycleView的Items
     */
    protected fun setRecycleViewAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter =mAdapter
        mAdapter.items=items
    }


    /**
     * 让子类去实现初始化一个View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View

    /**
     * 加载数据
     */
    protected abstract fun loadingData()

    /**
     * 显示对话框
     */
    protected fun showLoadingDialog() {
        mLoadingDialog?.show()
    }

    /**
     * 隐藏对话框
     */
    protected fun dismissLoadingDialog() {
        mLoadingDialog?.dismiss()

    }

    /**
     * 用于注册LiveData的观测者
     */
    protected open fun registerLiveDataObserver() {}
    override fun onLoadMore() {}
}