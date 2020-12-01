package com.hx.app.xinnews.mutitype.itemdata

import com.hx.app.xinnews.bean.NewsBean

data class NewsListItemData constructor(val news: NewsBean) {
    val title: String? = news.title
    val pic: String? = news.pic
    val time: String? = news.time
    val sources: String? = news.src
    val content: String? = news.content
}