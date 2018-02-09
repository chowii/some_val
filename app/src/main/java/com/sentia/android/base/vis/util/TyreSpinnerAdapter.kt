package com.sentia.android.base.vis.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.data.room.entity.LookupItem

/**
 * Created by chowii on 9/2/18.
 */
class TyreSpinnerAdapter(
        context: Context,
        private val data: List<LookupItem>
) : ArrayAdapter<LookupItem>(
        context,
        R.layout.support_simple_spinner_dropdown_item, android.R.id.text1) {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): LookupItem {
        return data[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getView(position, convertView, parent).apply {
            findViewById<TextView>(android.R.id.text1).apply {
                text = getItem(position).name
            }
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getDropDownView(position, convertView, parent).apply {
            findViewById<TextView>(android.R.id.text1).apply {
                text = getItem(position).name
            }
        }
    }
}




