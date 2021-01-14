package ua.kpi.comsys.iv7101.mobilefilms.ui.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.exp
import kotlin.math.roundToInt

class Chart @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attributeSet, defStyleAttr) {

    private val p = Paint()
    private val xBound = 6.0
    private val yBound = exp(xBound)
    private val step = 0.1f

    private lateinit var c: Canvas
    private var wOffset: Int = 0
    private var hOffset: Int = 0

    private var xAxisLength = 0f
    private var yAxisLength = 0f
    private var height = 0f

    private var oneX = 0f
    private var oneY = 0f
    private var yMark = 0f

    override fun onDraw(canvas: Canvas) {
        c = canvas
        wOffset = canvas.width / 2
        hOffset = 30

        xAxisLength = (wOffset - 10).toFloat()
        height = canvas.height.toFloat() - 13

        drawAxis()
        drawGraph()
    }

    private fun drawAxis() {
        p.color = Color.BLACK
        p.strokeWidth = 10f
        p.textSize = 50f
        p.textAlign = Paint.Align.CENTER

        fun drawLine(startX: Float, startY: Float, endX: Float, endY: Float) {
            c.drawLine(xOffset(startX), yOffset(startY), xOffset(endX), yOffset(endY), p)
        }

        oneX = (xAxisLength / xBound).roundToInt().toFloat()
        oneY = (height / yBound).roundToInt().toFloat()
        yAxisLength = yBound.toFloat() * oneY
        val mark = (yBound / 10).roundToInt()
        yMark = oneY * mark

        val arrowLength = 30

        // draw x axis
        drawLine(-xAxisLength, 0f, xAxisLength, 0f)

        // draw y axis
        drawLine(0f, -10f, 0f, yAxisLength)

        // draw x arrow
        drawLine(xAxisLength-3, 0f, xAxisLength - arrowLength -3, 0f + arrowLength)
        drawLine(xAxisLength-3, 0f, xAxisLength - arrowLength -3, 0f - arrowLength)

        // draw y arrow
        drawLine(0f, yAxisLength-3, 0f + arrowLength,yAxisLength - arrowLength -3)
        drawLine(0f, yAxisLength-3, 0f - arrowLength,yAxisLength - arrowLength -3)

        drawLine(oneX, -15f, oneX, 15f)
        drawLine(-15f, yMark, 15f, yMark)
        c.drawText(mark.toString(), xOffset(-50f), yOffset(yMark), p)
        c.drawText("1", xOffset(oneX), yOffset(30f), p)
    }

    private fun drawGraph() {
        p.color = Color.BLUE
        p.strokeWidth = 7f

        val points = mutableListOf<Float>()
        var x = - xAxisLength / oneX
        points.add(xOffset((x * oneX)))
        points.add(yOffset((exp(x) * oneY)))
        x += step
        while (x < xAxisLength / oneX) {
            val y = exp(x)
            points.add(xOffset((x * oneX)))
            points.add(yOffset((y * oneY)))
            points.add(xOffset((x * oneX)))
            points.add(yOffset((y * oneY)))
            x += step
        }
        c.drawLines(points.toFloatArray(), p)
    }

    private fun xOffset(x: Float): Float {return x + wOffset}
    private fun yOffset(y: Float): Float {return height - (y + hOffset)}
}