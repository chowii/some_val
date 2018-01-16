package com.sentia.android.base.vis.util

/**
 * Created by mariolopez on 11/1/18.
 */

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["android:src", "base64", "placeholder"], requireAll = false)
    fun setImageUrl(view: ImageView, src: String?, base64: String?, placeHolder: Drawable) {
        // part with base 64 is not tested because data is mocked
        if (!base64.isNullOrEmpty()) {
            // potentially heavy operation, must be done in non-ui thread
            val imageBytes = Base64.decode(base64, Base64.DEFAULT)
            Glide.with(view.context)
                    .load(imageBytes)
                    .asBitmap()
                    .placeholder(placeHolder)
                    .centerCrop()
                    .into(view)
        } else if (!src.isNullOrEmpty()) {
            Glide.with(view.context)
                    .load(src)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .into(view)
        }
    }
}