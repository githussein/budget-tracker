package com.example.budgettrackerchallenge.ui.utils

import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Double.toCurrency(locale: Locale = Locale.getDefault()): String {
    val format = NumberFormat.getCurrencyInstance(locale)
    return format.format(this)
}

fun LocalDateTime.toFormattedString(pattern: String = "dd MMM yyyy, HH:mm"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}