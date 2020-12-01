package com.hx.app.xinnews.mutitype.itemview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drakeet.multitype.ItemViewBinder
import com.hx.app.xinnews.R
import com.hx.app.xinnews.mutitype.itemdata.NewsListItemData
import com.hx.app.xinnews.myinterface.OnRecycleViewItemClickListener
import com.hx.app.xinnews.ui.CircleImageView
import java.lang.ref.WeakReference

class NewsListItemView constructor(context:Context) : ItemViewBinder<NewsListItemData,NewsListItemView.ViewHolder>(){
    val mWeakReference: WeakReference<Context> = WeakReference(context)
    var mRecycleViewItemClickListener: OnRecycleViewItemClickListener? = null

    constructor(context: Context,recycleViewItemClickListener: OnRecycleViewItemClickListener):this(context){
        this.mRecycleViewItemClickListener=recycleViewItemClickListener
    }

    override fun onBindViewHolder(holder: NewsListItemView.ViewHolder, item: NewsListItemData) {
        holder.src.text=item.sources
        holder.time.text=item.time
        holder.title.text=item.title
        mWeakReference.get()?.let { Glide.with(it).load(item.pic).error(R.drawable.error).placeholder(R.drawable.loading)
                .into(holder.imageView)}?:
                holder.imageView.setImageResource(R.drawable.error)
        holder.itemView.setOnClickListener(View.OnClickListener {
            mRecycleViewItemClickListener?.onItemClick(it, item)
        })
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): NewsListItemView.ViewHolder {
        val view:View= inflater.inflate(R.layout.news_items,parent,false)
        return ViewHolder(view)
    }

     inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById(R.id.title_text)
        val time:TextView=itemView.findViewById(R.id.time_text)
        val src:TextView=itemView.findViewById(R.id.sources_text)
        val imageView:CircleImageView=itemView.findViewById(R.id.image_view)
    }


}