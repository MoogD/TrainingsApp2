package com.example.trainingsapp.app.timer

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.example.trainingsapp.R

class CustomNumberPicker(context: Context, attrs: AttributeSet) : NumberPicker(context, attrs) {

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomNumberPicker,
            0,
            0
        ).apply {
            try {
                maxValue = getInt(R.styleable.CustomNumberPicker_maxValue, 59)
                minValue = getInt(R.styleable.CustomNumberPicker_minValue, 0)
            } finally {
                recycle()
            }
        }
    }
}
