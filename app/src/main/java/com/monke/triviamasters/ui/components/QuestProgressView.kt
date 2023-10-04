package com.monke.triviamasters.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.monke.triviamasters.R
import java.lang.Math.min

class QuestProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
): View(context, attrs, defStyleAttr, defStyleRes) {

    private val circlePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }
    private val backgroundPaint = Paint().apply {
        color = Color.GREEN

        isAntiAlias = true
        strokeWidth = 2f
    }

    private var angleStep = 0f
    private var circleCx = 0f
    private var circleCy = 0f
    private var outerCircleRadius = 0f
    private var innerCircleRadius = 0f
    private val circlePath = Path()
    private var innerRect = RectF()
    private var outerRect = RectF()

    var questionsCount = 0
        set(value) {
            field = value
            invalidate()
        }
    var currentQuestion = 1
        set(value) {
            field = value
            invalidate()
        }

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
        angleStep = 360f / questionsCount
        circleCx = width / 2f
        circleCy = height / 2f
        outerCircleRadius = width / 4f
        innerCircleRadius = width / 4.5f

        outerRect.set(circleCx - outerCircleRadius,
            circleCy - outerCircleRadius,
            circleCx + outerCircleRadius,
            circleCy + outerCircleRadius)
        innerRect.set(
            circleCx - innerCircleRadius,
            circleCy - innerCircleRadius,
            circleCx + innerCircleRadius,
            circleCy + innerCircleRadius
        )
    }

    override fun onDraw(canvas: Canvas) {
        var alpha = 270f - angleStep
        var questionCounter = 0
        while (alpha > -90f - angleStep) {
            circlePath.reset()
            circlePath.arcTo(outerRect, alpha, angleStep)
            circlePath.arcTo(innerRect, alpha + angleStep, -angleStep)
            circlePath.close()

            if (questionCounter < currentQuestion)
                canvas.drawPath(circlePath, backgroundPaint)
            else
                canvas.drawPath(circlePath, circlePaint)
            questionCounter++
            alpha -= angleStep
        }
    }

}