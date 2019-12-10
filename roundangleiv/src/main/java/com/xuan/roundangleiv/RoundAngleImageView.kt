package com.xuan.roundangleiv

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundAngleImageView : AppCompatImageView {

    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()

    private var defaultRadius = 0
    private var radius = 0
    private var leftTopRadius = 0
    private var rightTopRadius = 0
    private var rightBottomRadius = 0
    private var leftBottomRadius = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        // 读取配置
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView)
        radius = array.getDimensionPixelOffset(
            R.styleable.RoundAngleImageView_radius,
            defaultRadius
        )
        leftTopRadius = array.getDimensionPixelOffset(
            R.styleable.RoundAngleImageView_left_top_radius,
            defaultRadius
        )
        rightTopRadius = array.getDimensionPixelOffset(
            R.styleable.RoundAngleImageView_right_top_radius,
            defaultRadius
        )
        rightBottomRadius = array.getDimensionPixelOffset(
            R.styleable.RoundAngleImageView_right_bottom_radius,
            defaultRadius
        )
        leftBottomRadius = array.getDimensionPixelOffset(
            R.styleable.RoundAngleImageView_left_bottom_radius,
            defaultRadius
        )

        // 如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        array.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        val maxLeft = leftTopRadius.coerceAtLeast(leftBottomRadius)
        val maxRight = rightTopRadius.coerceAtLeast(rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop = leftTopRadius.coerceAtLeast(rightTopRadius)
        val maxBottom = leftBottomRadius.coerceAtLeast(rightBottomRadius)
        val minHeight = maxTop + maxBottom

        if (width >= minWidth && height > minHeight) {
            val path = Path()

            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius.toFloat(), 0f)
            path.lineTo(width - rightTopRadius, 0f)
            path.quadTo(width, 0f, width, rightTopRadius.toFloat())

            path.lineTo(width, height - rightBottomRadius)
            path.quadTo(width, height, width - rightBottomRadius, height)

            path.lineTo(leftBottomRadius.toFloat(), height)
            path.quadTo(0f, height, 0f, height - leftBottomRadius)

            path.lineTo(0f, leftTopRadius.toFloat())
            path.quadTo(0f, 0f, leftTopRadius.toFloat(), 0f)

            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }
}