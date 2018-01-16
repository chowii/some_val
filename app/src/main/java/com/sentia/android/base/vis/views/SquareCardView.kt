package com.sentia.android.base.vis.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet

class SquareCardView : CardView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}