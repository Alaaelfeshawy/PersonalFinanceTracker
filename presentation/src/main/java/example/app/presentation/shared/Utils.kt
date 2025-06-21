package example.app.presentation.shared

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long?): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return millis?.let { Date(it) }?.let { formatter.format(it) } ?: ""
}