package com.sentia.android.base.vis.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jovan on 18/9/17.
 */

// MARK: - ================== Common formats used ==================

sealed class DateFormat {

    class Iso8601DateTime : DateFormat()
    class MonthAbbreviationFormat : DateFormat()
    class DateDisplayFormat : DateFormat()
    class TimeFormat : DateFormat()

}

fun DateFormat.stringFormat(): String {
    val format = when (this) {
        is DateFormat.Iso8601DateTime -> "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        is DateFormat.MonthAbbreviationFormat -> "dd MMM yyyy"
        is DateFormat.DateDisplayFormat -> "dd/MM/yy"
        is DateFormat.TimeFormat -> "h:mma"
    }
    return format
}

// MARK: - ================== Methods ==================

/**
 * Returns a string representation of the date
 * @param stringFormat the expected date format to parse
 * @return string representation of date
 */
fun Date.toStringWithFormat(stringFormat: String): String {
    val format: SimpleDateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    if (stringFormat == DateFormat.Iso8601DateTime().stringFormat()) {
        format.timeZone = TimeZone.getTimeZone("UTC")
    }
    try {
        return format.format(this)
    } catch (e: ParseException) {
        Log.e("date parse error ", "Exception during parsing date to string", e)
        return ""
    }
}

fun Date.durationFromNow(): Date {
    val durationLong = System.currentTimeMillis() - this.time
    return Date(durationLong)
}

//fun Date.durationFromNowAsString(): String {
//    val duration = this.durationFromNow()
//    Calendar.getInstance().let {
//        it.time = duration
//        return when {
//            it[Calendar.DAY_OF_YEAR] < 1 -> getHourDuration(it[Calendar.HOUR])
//            it[Calendar.DAY_OF_YEAR] < 29 -> getDayDuration(it[Calendar.MONTH])
//            else -> getMonthDuration(it[Calendar.MONTH])
//        }
//    }
//}

//private fun getMonthDuration(month: Int) = SohoApplication.getContext()
//        .resources.getQuantityString(R.plurals.month, month, month)

//private fun getDayDuration(month: Int) = SohoApplication.getContext()
//        .resources.getQuantityString(R.plurals.month, month, month)
//
//private fun getHourDuration(hour: Int) = SohoApplication.getContext()
//        .resources.getQuantityString(R.plurals.hour, hour, hour)

