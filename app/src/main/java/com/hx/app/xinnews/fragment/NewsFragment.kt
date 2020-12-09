package com.hx.app.xinnews.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.hx.app.xinnews.activity.NewsContentActivity
import com.hx.app.xinnews.base.BaseFragment
import com.hx.app.xinnews.bean.Items
import com.hx.app.xinnews.constant.CONTENT
import com.hx.app.xinnews.constant.TAB_TITLE
import com.hx.app.xinnews.constant.TITLE
import com.hx.app.xinnews.databinding.NewsListFagmentBinding
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData
import com.hx.app.xinnews.mutitype.itemview.NewsListItemView
import com.hx.app.xinnews.myinterface.OnRecycleViewItemClickListener
import com.hx.app.xinnews.viewmodel.MainViewModel


class NewsFragment : BaseFragment(), OnRefreshListener {
    private var mStartIndex = 20
    private lateinit  var mBinding: NewsListFagmentBinding
    private lateinit var mChannel: String

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        inflater?.let {
            NewsListFagmentBinding.inflate(it, container, false)
        }.also {
           it?.let {
               mBinding=it
           }
        }
        mBinding.recycleView.layoutManager = LinearLayoutManager(context)
        mBinding.recycleView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter.attach(mBinding.recycleView)
        mBinding.swipeRefreshLayout.setOnRefreshListener(this)
        return mBinding.root
    }

    override fun registerItems() {
        super.registerItems()
        context?.let {
            mAdapter.register(NewsListItemData::class.java, NewsListItemView(it, object : OnRecycleViewItemClickListener {
                override fun onItemClick(view: View?, data: NewsListItemData) {
                    val intent = Intent(context, NewsContentActivity().javaClass).apply {
                        putExtra(CONTENT, data.content)
                        putExtra(TITLE, data.title)
                    }
                    startActivity(intent)
                }
            }))
            setRecycleViewAdapter(mBinding.recycleView)
        }

    }

    override fun loadingData() {
        arguments?.let {
            mChannel = it.getString(TAB_TITLE, "头条")
        }
        mViewModel.getNewsTop20(mChannel)
    }

    override fun registerLiveDataObserver() {
        mViewModel.mNewsListLiveData.observe(this, Observer<List<NewsListItemData>> { newsListItemData ->
            items.addAll(newsListItemData)
            mAdapter.notifyDataSetChanged()
            dismissLoadingDialog()
            mBinding.swipeRefreshLayout.isRefreshing = false
        })

        mViewModel.mLoadingMoreNewsLiveData.observe(this, Observer<List<NewsListItemData>> { newsListItemData ->
            val items = Items()
            items.addAll(newsListItemData)
            mAdapter.addData(items)
            mAdapter.loadingComplete()
        })
    }


    override fun onLoadMore() {
        mViewModel.getMoreNews(mChannel, mStartIndex, NUM)
        mStartIndex += NUM
    }

    //进行下来刷新的操作
    override fun onRefresh() {
        if (!items.isEmpty()) {
            items.clear()
        }
        mStartIndex = 20
        mViewModel.getNewsTop20(mChannel)
    }

    companion object {
        //每次加载更多的时候加载10条
        private const val NUM = 10
    }
}

