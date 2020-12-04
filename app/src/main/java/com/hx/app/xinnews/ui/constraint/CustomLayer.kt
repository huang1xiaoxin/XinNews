package com.hx.app.xinnews.ui.constraint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.hx.app.xinnews.R

class CustomLayer @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttrs: Int = 0) : CustomConstraintHelper(context, attr, defStyleAttrs) {


    companion object {
        const val PLUS_TEXT = "+"
        const val REDUCE_TEXT = "-"
    }


    private var mTagText = PLUS_TEXT

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * X偏移量集合
     */
    private val offsetXs: MutableList<Float> = mutableListOf()

    /**
     * y偏移量集合
     */
    private val offsetYs: MutableList<Float> = mutableListOf()

    init {
        mPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            color = Color.parseColor("#66000000")
            strokeWidth = 1.0F
            textSize = 60.0F
        }

        mRectPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#66000000")
            strokeWidth = 3.0F
        }

    }


    /**
     * 在绘制控件背景时，对标记控件进行绘制
     */
    override fun drawTreasure(customConstraintLayout: CustomConstraintLayout, canvas: Canvas?) {
        ids.forEachIndexed { index, id ->
            val view = customConstraintLayout.findViewById<View>(id)
            val x = view.right - offsetXs.getOrElse(index) { 1.0f }.dp2px()
            val y = view.top + offsetYs.getOrElse(index) { 10.0f }.dp2px()
            val rect = Rect(view.left - 36, view.top - 15, view.right + 36, view.bottom + 15)
            canvas?.drawRect(rect, mRectPaint)
            canvas?.drawText(mTagText, x, y, mPaint)
        }

    }

    override fun readAttrs(attrs: AttributeSet?) {
        super.readAttrs(attrs)
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.CustomLayer).let { ta ->
                {
                    //这里ta.getString(R.styleable.CustomLayer_referenced_offset_x)数组越界异常,原因还没找到
                    handlerAttributes(ta.getString(R.styleable.CustomLayer_referenced_offset_x),
                            ta.getString(R.styleable.CustomLayer_referenced_offset_y))
                    ta.recycle()
                }

            }
        }

    }

    fun setTagDisplay(str: String) {
        mTagText = str
        //触发父类进行刷新
        (parent as? CustomConstraintLayout)?.invalidate()
    }

    private fun handlerAttributes(x: String?, y: String?) {
        x?.let { str ->
            str.split(",").forEach {
                offsetXs.add(it.trim().toFloat())
            }
        }

        y?.let { str ->
            {
                str.split(",").forEach {
                    offsetYs.add(it.trim().toFloat())
                }
            }
        }
    }

    private fun Float.dp2px(): Float {
        val scale = resources.displayMetrics.density
        return scale * this + 0.5f
    }
}