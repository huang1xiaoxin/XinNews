package com.hx.app.xinnews.viewmodel

import android.view.Display
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hx.app.xinnews.model.Model
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel :ViewModel()  {

    private val model=Model()

    /**
     * 存放频道的LiveData
     */
    private val mChannels:MutableLiveData<List<String>> = MutableLiveData();

    private val mNewsListLiveData:MutableLiveData<List<NewsListItemData>> = MutableLiveData()

    /**
     * 创建协程来获取频道的数据
     */
    fun getChannel(){
        viewModelScope.launch(Dispatchers.IO) {
            val data=model.getChannel()
            mChannels.postValue(data)
        }
    }

    fun  getChannelLiveData():LiveData<List<String>>{
        return mChannels;
    }
    fun getNewsListLiveData():LiveData<List<NewsListItemData>>{
        return mNewsListLiveData
    }

    fun getNewsTop20( channel:String){
        viewModelScope.launch (Dispatchers.IO){
            val list:MutableList<NewsListItemData> = mutableListOf()
            val data =model.getNewsTop20(channel)
            data.result.list.forEach {
                list.add(NewsListItemData(it))
            }
            mNewsListLiveData.postValue(list);
        }
    }




}