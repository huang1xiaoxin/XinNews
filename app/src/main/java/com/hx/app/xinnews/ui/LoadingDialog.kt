package com.hx.app.xinnews.ui

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hx.app.xinnews.R

/**
 * 这里要继承Dialog，继承AlertDialog显示不了
 */
class LoadingDialog constructor(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    constructor(context: Context) : this(context, 0) {

    }

    /**
     * 默认为静态内部的类，如果是非静态的内部类，需要添加关键字inner
     */
    class Builder constructor(context: Context) {


        private var context: Context = context;
        private var showMessage: String = "正在加载。。。"
        private var isShowMessage = true
        private var isCancelable = false
        private var isCancelOutside = false

        /**
         * 是否显示加载提示信息
         */
        fun setShowMessage(isShowMessage: Boolean): Builder {
            this.isShowMessage = isShowMessage
            return this
        }

        /**
         * 加载提示信息
         */
        fun setMessage(message: String): Builder {
            this.showMessage = message
            return this
        }


        /**
         * 是否可以点击返回键取消
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 是否可以点击外边取消
         */
        fun setCancelOutside(isCancelOutside: Boolean): Builder {
            this.isCancelOutside = isCancelOutside
            return this
        }

        fun create(): LoadingDialog {
            val view: View = LayoutInflater.from(context).inflate(R.layout.dailog_layout, null)
            val loadingDialog = LoadingDialog(context, R.style.CustomDialog)
            val message: TextView = view.findViewById(R.id.text_view)
            message.text = showMessage
            message.visibility = if (isShowMessage) View.VISIBLE else View.GONE
            loadingDialog.setCancelable(isCancelable)
            loadingDialog.setCanceledOnTouchOutside(isCancelOutside)
            loadingDialog.setContentView(view)
            return loadingDialog
        }

    }


}