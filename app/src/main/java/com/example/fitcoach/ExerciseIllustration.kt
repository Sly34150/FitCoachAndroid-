package com.example.fitcoach

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ExerciseIllustration @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val p = Paint(Paint.ANTI_ALIAS_FLAG)
    private var exerciseIndex = 0
    private var isStretch = false
    fun setExercise(index: Int, stretch: Boolean) { exerciseIndex = index; isStretch = stretch; invalidate() }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat(); val h = height.toFloat()
        p.color = 0xFFE4F0EB.toInt(); p.style = Paint.Style.FILL
        canvas.drawRoundRect(0f, 0f, w, h, 24f, 24f, p)
        p.color = 0xFF137D69.toInt(); p.strokeWidth = 7f; p.style = Paint.Style.STROKE; p.strokeCap = Paint.Cap.ROUND
        val cx = w * .5f; val base = h * .72f
        // Simple instructional stick figure with movement arrows; schematic, not anatomy-specific.
        canvas.drawCircle(cx, h*.22f, 19f, p)
        val bent = exerciseIndex % 2 == 0
        canvas.drawLine(cx, h*.30f, cx, h*.52f, p)
        if (isStretch) {
            canvas.drawLine(cx, h*.37f, cx-w*.17f, h*.44f, p)
            canvas.drawLine(cx, h*.37f, cx+w*.17f, h*.44f, p)
            canvas.drawLine(cx, h*.52f, cx-w*.12f, base, p)
            canvas.drawLine(cx, h*.52f, cx+w*.12f, base, p)
        } else if (bent) {
            canvas.drawLine(cx, h*.37f, cx-w*.16f, h*.48f, p)
            canvas.drawLine(cx-w*.16f, h*.48f, cx-w*.23f, h*.39f, p)
            canvas.drawLine(cx, h*.37f, cx+w*.16f, h*.48f, p)
            canvas.drawLine(cx+w*.16f, h*.48f, cx+w*.23f, h*.39f, p)
            canvas.drawLine(cx, h*.52f, cx-w*.13f, base, p)
            canvas.drawLine(cx, h*.52f, cx+w*.13f, base, p)
        } else {
            canvas.drawLine(cx, h*.37f, cx-w*.2f, h*.32f, p)
            canvas.drawLine(cx, h*.37f, cx+w*.2f, h*.32f, p)
            canvas.drawLine(cx, h*.52f, cx-w*.12f, base, p)
            canvas.drawLine(cx, h*.52f, cx+w*.12f, base, p)
        }
        p.color = 0xFFEA8A35.toInt(); p.strokeWidth = 6f
        val path = Path().apply { moveTo(w*.76f, h*.48f); lineTo(w*.86f, h*.48f); lineTo(w*.82f, h*.43f); moveTo(w*.86f, h*.48f); lineTo(w*.82f, h*.53f) }
        canvas.drawPath(path, p)
        p.style = Paint.Style.FILL
    }
}
