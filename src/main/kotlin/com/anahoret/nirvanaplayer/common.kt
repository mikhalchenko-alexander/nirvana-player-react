package com.anahoret.nirvanaplayer

fun Number.toTimeString(): String {
  fun Int.zeroPrefixed(): String = this.toString().padStart(2, '0')

  val time = this.toDouble()
  val hours = kotlin.math.floor(time / 3600).toInt()
  val minutes = kotlin.math.floor(time % 3600 / 60).toInt()
  val seconds = kotlin.math.floor(time % 60).toInt()

  val timeParts =
    if (hours == 0) listOf(minutes, seconds)
    else listOf(hours, minutes, seconds)

  return timeParts.joinToString(separator = ":") { it.zeroPrefixed() }
}
