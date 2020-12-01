package com.hx.app.xinnews.myinterface

import android.view.View
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData


interface OnRecycleViewItemClickListener {
    fun onItemClick(view: View?, data: NewsListItemData)
}