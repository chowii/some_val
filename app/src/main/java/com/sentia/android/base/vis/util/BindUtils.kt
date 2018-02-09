package com.sentia.android.base.vis.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sentia.android.base.vis.data.room.entity.LookupItem
import java.util.*

/**
 * Created by mariolopez on 19/1/18.
 */

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean?) {
    view.visibility = if (value.orFalse()) View.VISIBLE else View.GONE
}

@BindingAdapter("year")
fun setYear(textView: TextView, date: String?) {
    date?.isNotEmpty()?.let {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = DateUtils.toTimeInMillis(date)
        textView.text = calendar.get(Calendar.YEAR).toString()
    }
}

@BindingAdapter(value = ["android:entries"], requireAll = false)
fun setTyreBrand(spinner: Spinner, date: List<LookupItem>?) {
    date?.let { spinner.adapter = TyreSpinnerAdapter(spinner.context, it) }
}

@BindingAdapter("android:onItemSelected")
fun setOnItemSelected(spinner: Spinner, selectedListener: AdapterView.OnItemSelectedListener?) {
    spinner.onItemSelectedListener = selectedListener
}

@BindingAdapter("app:isRefreshing")
fun showSpinningRefreshLayout(srl: SwipeRefreshLayout, value: Boolean) {
    srl.isRefreshing = value
}

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
        } else {
            view.setImageBitmap(null)
        }
    }
}