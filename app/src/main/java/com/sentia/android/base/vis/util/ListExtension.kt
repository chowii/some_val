package com.sentia.android.base.vis.util

import com.sentia.android.base.vis.data.room.entity.Accessory

fun List<Accessory>.toDisplayableString() = joinToString(separator = ", ") { it.name }