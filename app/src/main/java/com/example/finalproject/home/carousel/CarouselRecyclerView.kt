package com.example.finalproject.home.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CarouselRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): RecyclerView(context, attrs, defStyle) {
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(e)
    }
}