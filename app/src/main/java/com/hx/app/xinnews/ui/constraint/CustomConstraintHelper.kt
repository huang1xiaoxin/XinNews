package com.hx.app.xinnews.ui.constraint

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.hx.app.xinnews.R

abstract class CustomConstraintHelper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    //用于存储需要关联的Ids
    protected val ids: MutableList<Int> = mutableListOf()

    //进行初始化
    init {
        readAttrs(attrs)
    }

    open fun readAttrs(attrs: AttributeSet?) {
        attrs?.let { attrs ->
            context.obtainStyledAttributes(attrs, R.styleable.CustomConstraintHelper).let {
                handlerIds(it.getString(R.styleable.CustomConstraintHelper_referenced_ids))
                it.recycle()
            }

        }
    }

    private fun handlerIds(idString: String?) {
        idString?.let { str ->
            str.split(",").forEach {
                ids.add(resources.getIdentifier(it.trim(), "id", context.packageName))
            }
        }

    }

    /**
     * 在绘制控件背景时，对标记控件进行绘制
     */
    abstract fun drawTreasure(customConstraintLayout: CustomConstraintLayout, canvas: Canvas?)
}