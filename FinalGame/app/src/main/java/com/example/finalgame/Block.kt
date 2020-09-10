package com.example.finalgame

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

class Block constructor(context: Context, private val size: Int = 4) : FrameLayout(context) {
    var num = 0
        set(num) {
            field = num
            label.text = if (num <= 0) "" else "$num"
            label.textSize = (if (num >= 128) 160 / size else 192 / size).toFloat()
            label.setTextColor (Color.rgb(255, 255, 255)) /* white */


            val shape = GradientDrawable()
            shape.cornerRadius = (50 / size).toFloat()

            when (num) {
                0 -> shape.setColor(Color.rgb(204, 204, 204)) /* grey */
                2 -> shape.setColor(Color.rgb(135, 206, 250))  /* light blue */
                4 -> shape.setColor(Color.rgb(0, 191, 255)) /* deep sky blue */
                8 -> shape.setColor(Color.rgb(30, 144, 255)) /* dodgerblue */
                16 -> shape.setColor(Color.rgb(0, 0, 255)) /* blue */
                32 -> shape.setColor(Color.rgb(0, 0, 205)) /* medium blue */
                64 -> shape.setColor(Color.rgb(0, 0, 128)) /* navy */
                128 -> shape.setColor(Color.rgb(123, 104, 238)) /* medium slate blue */
                256 -> shape.setColor(Color.rgb(72, 61, 139)) /* dark slate blue */
                512 -> shape.setColor(Color.rgb(139, 0, 139)) /* dark magenta */
                1024 -> shape.setColor(Color.rgb(128, 0, 128)) /* purple */
                2048 -> shape.setColor(Color.rgb(75, 0, 130)) /* indigo */
            }
            label.background = shape
        }
    private val label = TextView(getContext())

    init {
        label.gravity = Gravity.CENTER
        val lp = FrameLayout.LayoutParams(-1, -1)
        lp.setMargins(15, 15, 15, 15)
        addView(label, lp)
    }
}