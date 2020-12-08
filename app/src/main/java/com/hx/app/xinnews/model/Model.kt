package com.hx.app.xinnews.model

import com.hx.app.xinnews.bean.ChannelNews
import com.hx.app.xinnews.bean.ResultContent
import com.hx.app.xinnews.constant.NET_ERROR
import com.hx.app.xinnews.net.APP_KEY
import com.hx.app.xinnews.net.RetrofitApi

class Model {


    suspend fun getNews(channel: String, num: Int, start: Int): ChannelNews? {
        val result: ResultContent<ChannelNews> = RetrofitApi.create().getNews(channel, num, start, APP_KEY)
        return result.result
    }

    /**
     * 获取前20条新闻
     */
    suspend fun getNewsTop20(channel: String): ChannelNews? {
        return getNews(channel, 20, 0)
    }

    /**
     * 获取频道
     */
    suspend fun getChannel(): List<String> {
        val result = RetrofitApi.create().getChannel(APP_KEY).result
        return result?.result ?: listOf(NET_ERROR)

    }

}