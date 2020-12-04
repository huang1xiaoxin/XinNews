package com.hx.app.xinnews.ui.constraint

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 如果不想写三个构造函数的，可以通过 @JvmOverloads来标记，就不会报错
 */
class CustomConstraintLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0) : ConstraintLayout(context, attrs, defStyleAttrs) {

    //用来存储Treasure
    private val mCustomConstraintHelpers: MutableList<CustomConstraintHelper> = mutableListOf()

    init {
        //这行代码是必须的，否则不能在容器控件画布绘制图案
        setWillNotDraw(false)
    }

    /**
     * 对View进行添加
     */
    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
        //为了避免抛出异常，可以使用安全转换操作符 as?,它可以在失败时返回 null
        (view as? CustomConstraintHelper)?.let {
            if (!mCustomConstraintHelpers.contains(it)) {
                mCustomConstraintHelpers.add(it)
            }
        }

    }

    /**
     * 移除View
     */
    override fun onViewRemoved(view: View?) {
        super.onViewRemoved(view)
        (view as? CustomConstraintHelper)?.let {
            if (mCustomConstraintHelpers.contains(it)) {
                mCustomConstraintHelpers.remove(it)
            }
        }
    }

    /**
     * 绘制背景时，通知标记控件绘制
     */
    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        mCustomConstraintHelpers.forEach {
            it.drawTreasure(this, canvas)
        }
    }

}