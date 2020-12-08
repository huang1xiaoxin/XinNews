package com.hx.app.xinnews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Layer
import com.hx.app.xinnews.R
import com.hx.app.xinnews.ui.constraint.CustomLayer

class GrideViewAdapter(lists: MutableList<String>, context: Context,val tag:String) : BaseAdapter() {

    private val strList: MutableList<String> = lists

    private val mContext = context

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View? {
        var view: View? = null
        var viewHolder: ViewHolder? = null
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.channel_manage_item, p2, false)
            viewHolder = ViewHolder()
            viewHolder.textView = view.findViewById(R.id.text)
            viewHolder.layer = view.findViewById(R.id.layer)

            view.tag = viewHolder
        } else {
            view = convertView
            (view.tag as? ViewHolder)?.let {
                viewHolder = it
            }
        }
        viewHolder?.apply {
            textView?.text = strList[position]
            layer?.setTagDisplay(tag)
        }
        return view

    }

    override fun getItem(p0: Int): Any {
        return strList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return strList.size
    }

    inner class ViewHolder {
        var textView: TextView? = null
        var layer: CustomLayer? = null
    }

}