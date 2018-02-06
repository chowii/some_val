package com.sentia.android.base.vis.util

/**
 * Created by mariolopez on 18/1/18.
 */
import com.sentia.android.base.vis.util.DateFormat.*
import org.jetbrains.anko.AnkoLogger
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


object DateUtils : AnkoLogger {
    private val FORMAT_FOR_FILE_NAME = "yyyyMMdd_HHmmss"
    private val FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val FORMAT_DISPLAYABLE = "dd MMM yyyy"
    private val FORMAT_TIME = "hh:mma"
    private val FORMAT_DATE_FROM_SERVER = "yyyy-MM-dd"

    val dateFormatForFileName: String
        get() = SimpleDateFormat(FORMAT_FOR_FILE_NAME, Locale.getDefault()).format(Date())

    fun toInspectionDurationString(startTime: Long?, endTime: Long?): String {
        if (startTime == null || endTime == null) {
            return ""
        }
        val start = SimpleDateFormat(FORMAT_TIME, Locale.getDefault()).format(Date(startTime))
        val end = SimpleDateFormat(FORMAT_TIME, Locale.getDefault()).format(Date(endTime))
        return String.format("%s - %s", start, end)
    }

    fun timeToDisplayableString(time: Long): String {
        return SimpleDateFormat(FORMAT_DISPLAYABLE, Locale.getDefault()).format(Date(time))
    }

    fun timeToQueryString(time: Long): String {
        return SimpleDateFormat(FORMAT_ISO8601, Locale.getDefault()).format(Date(time))
    }

    fun iso8601TimeToLong(time: String?): Long? {
        var result: Long? = null
        if (time != null && !time.isEmpty()) {
            val simpleDateFormat = SimpleDateFormat(FORMAT_ISO8601, Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            try {
                result = simpleDateFormat.parse(time).time
            } catch (e: ParseException) {
                error { "Error during parsing a date : " + e.message }
            }

        }
        return result
    }

    fun toTimeInMillis(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return calendar.timeInMillis
    }

    fun toTimeInMillis(date: String): Long {
        var result: Long? = null
        try {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE_FROM_SERVER, Locale.getDefault())
            result = simpleDateFormat.parse(date).time
        } catch (e: ParseException) {
            error { "Error during parsing a date : " + e.message }
        }
        return result
    }
}

/**
 * Returns a string representation in the format specified of the long date
 * @param format format of output string
 * @return returns string representation of long date
 */
fun Long.stringWithFormat(format: String) =
        Date(this).toStringWithFormat(format)

// MARK: - ================== Common formats ==================

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'YYYY-MM-dd'T'HH:mm:ss.SSSZ' format
 */
fun Long?.toDateLongWithIso8601DateTimeFormat() =
        toStringWithFormat(this, Iso8601DateTime())

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long?.toStringWithDisplayFormat() =
        toStringWithFormat(this, DateDisplayFormat())

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long?.toStringWithTimeFormat() =
        toStringWithFormat(this, TimeFormat())

private fun toStringWithFormat(value: Long?, dateFormat: DateFormat)
        = if (value != null) Date(value).toStringWithFormat(dateFormat.stringFormat()) else ""

