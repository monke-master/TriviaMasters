package com.monke.triviamasters.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.monke.triviamasters.R
import java.lang.Math.PI
import java.lang.Math.min
import kotlin.math.cos
import kotlin.math.sin

class QuestProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
): View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint()
    private var questionsCount = 0
    private var angleStep = 2*PI
    private var circleCx = 0f
    private var circleCy = 0f
    private var circleRadius = 0f

    init {
        attrs?.let { attrs ->
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.QuestProgressView,
                defStyleAttr,
                defStyleRes
            )
            questionsCount = typedArray.getInteger(R.styleable.QuestProgressView_questionsCount, 0)
            typedArray.recycle()
        }

    }

    var currentQuestion = 1
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 100
        val desiredHeight = 100

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
        angleStep = 2* PI / questionsCount
        circleCx = width / 2f
        circleCy = height / 2f
        circleRadius = width / 4f
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.RED
        canvas.drawCircle(circleCx, circleCy, circleRadius, paint)
        paint.color = Color.BLACK

        var alpha = PI / 2
        while (alpha < 2.5* PI) {
            var nx = circleRadius * cos(alpha) + circleCx
            var ny = circleCy - circleRadius * sin(alpha)
            canvas.drawLine(circleCx, circleCy, nx.toFloat(), ny.toFloat(), paint)
            alpha += angleStep
        }
    }


}