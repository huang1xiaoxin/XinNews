package com.hx.app.xinnews.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hx.app.xinnews.bean.SharedPreferencesUtil
import com.hx.app.xinnews.model.Model
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.Key

class MainViewModel : ViewModel() {

    private val model = Model()

    /**
     * 存放频道的LiveData
     */
    private val mChannels: MutableLiveData<List<String>> = MutableLiveData()

    private val mNewsListLiveData: MutableLiveData<List<NewsListItemData>> = MutableLiveData()

    private val mLoadingMoreNewsLiveData: MutableLiveData<List<NewsListItemData>> = MutableLiveData()

    private val mMyChannelLiveData:MutableLiveData<List<String>> = MutableLiveData()
    private val mHotChannelLiveData:MutableLiveData<List<String>> = MutableLiveData()

    /**
     * 创建协程来获取频道的数据
     */
    fun getChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = model.getChannel()
            mChannels.postValue(data )
        }
    }

    fun getChannelLiveData(): LiveData<List<String>> {
        return mChannels
    }

    fun getNewsListLiveData(): LiveData<List<NewsListItemData>> {
        return mNewsListLiveData
    }

    fun getLoadingMoreNewsLiveData(): LiveData<List<NewsListItemData>> {
        return mLoadingMoreNewsLiveData
    }
    fun getMyChannelLiveData():LiveData<List<String>>{
        return mMyChannelLiveData
    }
    fun getHotChannelLiveData():LiveData<List<String>>{
        return mHotChannelLiveData
    }

    fun getNewsTop20(channel: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<NewsListItemData> = mutableListOf()
            val data = model.getNewsTop20(channel)
            data?.result?.list?.forEach {
                list.add(NewsListItemData(it))
            }
            mNewsListLiveData.postValue(list)
        }
    }

    fun getMoreNews(channel: String, start: Int, num: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<NewsListItemData> = mutableListOf()
            val data = model.getNews(channel, num, start)
            data?.result?.list?.forEach {
                list.add(NewsListItemData(it))
            }
            mLoadingMoreNewsLiveData.postValue(list)
        }
    }

    /**
     * 获取我的频道
     */
    fun getMyChannel(context: Context,key:String,defValue:String){
        val myChannels by SharedPreferencesUtil(context,key,defValue)
        val list = mutableListOf<String>()
        myChannels.split(" ").forEach {
            if (it != "") {
                list.add(it)
            }
        }
        mMyChannelLiveData.value=list
    }

    /**
     * 获取热门的pind
     */
    fun getHotChannel(context: Context,key:String,defValue:String){
        val hotChannel by SharedPreferencesUtil(context,key,defValue)
        val list= mutableListOf<String>()
        hotChannel.split(" ").forEach{
            if(it !=""){
                list.add(it)
            }
        }
        mHotChannelLiveData.value=list
    }

}