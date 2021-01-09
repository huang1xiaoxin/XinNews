package com.hx.app.xinnews.net

import com.hx.app.xinnews.bean.Channel
import com.hx.app.xinnews.bean.ChannelNews
import com.hx.app.xinnews.bean.ResultContent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//京东云新闻的APP_KEY
const val APP_KEY = "a790c24d2f8437362f"
const val BASE_URL = "https://way.jd.com/jisuapi/"

interface RetrofitApi {
    @GET("channel")
    suspend fun getChannel(@Query("appkey") key: String): ResultContent<Channel>

    @GET("get")
    suspend fun getNews(@Query("channel") channel: String, @Query("num") num: Int,
                        @Query("start") start: Int, @Query("appkey") key: String): ResultContent<ChannelNews>

    companion object {

        fun create(): RetrofitApi {
            return Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitApi::class.java)
        }
    }
}
