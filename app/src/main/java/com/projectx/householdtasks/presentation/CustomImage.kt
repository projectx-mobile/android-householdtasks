package com.projectx.householdtasks.presentation

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.projectx.householdtasks.R
import kotlin.math.cos
import kotlin.math.sin


class CustomImage(context: Context, attributes: AttributeSet? = null) :
    AppCompatImageView(context, attributes) {

//    private var progress = 49
//    private var progressColor = Color.GREEN
//    private var strokeColor = Color.BLACK
//    private var strokeWidth = 4F
//    private var progressWidth = 20F

    private var progress = attributes?.getAttributeIntValue(
        "http://schemas.android.com/apk/res-auto",
        "progress",
        0
    ) ?: 0
    @RequiresApi(Build.VERSION_CODES.M)
    private var progressColor = attributes?.getAttributeIntValue(
        "http://schemas.android.com/apk/res-auto",
        "progressColor",
        context.resources.getColor(R.color.primary_juicy_grape)
    ) ?: context.resources.getColor(R.color.primary_juicy_grape)
    private var strokeColor = attributes?.getAttributeIntValue(
        "http://schemas.android.com/apk/res-auto",
        "strokeColor",
        Color.BLACK
    ) ?: Color.BLACK
    private var strokeWidth = attributes?.getAttributeFloatValue(
        "http://schemas.android.com/apk/res-auto",
        "borderStrokeWidth",
        4F
    ) ?: 4F
    private var progressWidth = attributes?.getAttributeFloatValue(
        "http://schemas.android.com/apk/res-auto",
        "progressWidth",
        8F
    ) ?: 8F


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }

    override fun onDraw(canvas: Canvas?) {
        val drawable = drawable ?: return
        if (width == 0 || height == 0) {
            return
        }
        val w = width
        val halfWidth = (w / 2).toFloat()

        val paint = Paint()
        paint.color = context.resources.getColor(R.color.primary_white_snow)
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0F, 0F, halfWidth, w.toFloat(), paint)

        val b = drawableToBitmap(drawable)
        val radius = w - strokeWidth / 2 - progressWidth

        val roundBitmap = getCroppedBitmap(Bitmap.createScaledBitmap(b, w, w, false), w)
        canvas?.drawBitmap(roundBitmap, null, RectF(strokeWidth / 2 + progressWidth, strokeWidth / 2 + progressWidth, radius, radius), null)

        val radiusBig = halfWidth - strokeWidth / 2
        val radiusSmall = halfWidth - strokeWidth / 2 - progressWidth

        val oval = RectF()
        val sweepAngle: Float = 360 * progress / 100F
        val progressRadius = (radiusBig + radiusSmall) / 2
        val distFromEnd = (radiusBig - radiusSmall) / 2 + strokeWidth / 2
        val sweepAngleRad: Float = Math.toRadians(sweepAngle.toDouble()).toFloat()
        val circleRadius = progressWidth / 2

        oval.set(distFromEnd,
            distFromEnd,
            w - distFromEnd,
            w - distFromEnd)

        paint.strokeWidth = progressWidth
        paint.style = Paint.Style.STROKE
        paint.color = Color.WHITE
        canvas?.drawArc(oval, -90F, 360F, false, paint)

        paint.color = progressColor
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = circleRadius
        paint.style = Paint.Style.FILL


        canvas?.drawCircle(
            halfWidth + sin(sweepAngleRad) * progressRadius,
            halfWidth - cos(sweepAngleRad) * progressRadius,
            circleRadius,
            paint
        )

        canvas?.drawCircle(
            halfWidth + 2,
            (radiusBig - radiusSmall) / 2 + 2,
            circleRadius,
            paint
        )

        oval.set(distFromEnd,
            distFromEnd,
            w - distFromEnd,
            w - distFromEnd)
        paint.strokeWidth = progressWidth
        paint.style = Paint.Style.STROKE
        paint.color = progressColor
        canvas?.drawArc(oval, -90F, sweepAngle, false, paint)


        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = strokeColor

        oval.set(halfWidth - radiusBig,
            halfWidth - radiusBig,
            halfWidth + radiusBig,
            halfWidth + radiusBig)
        canvas?.drawArc(oval, 0F, 360F, true, paint)

        oval.set(halfWidth - radiusSmall,
            halfWidth - radiusSmall,
            halfWidth + radiusSmall,
            halfWidth + radiusSmall)
        canvas?.drawArc(oval, 0F, 360F, true, paint)

    }

    private fun getCroppedBitmap(bitmap: Bitmap, w: Int): Bitmap {
        val output = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, w, w)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        val r = w / 2f
        canvas.drawCircle(r, r, r, paint)
        paint.xfermode =
            PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun setProgress(progress: Int) {
        this.progress = progress - 1
        invalidate()
    }
}