package ch.fhnw.petra.poodle.misc

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TemporalHelper {
    companion object {
        fun instantFromDateTimeString(date: String, time: String): Instant {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val localDateTime = LocalDateTime.parse("$date $time", formatter)
            return localDateTime.toInstant(ZoneOffset.UTC)
        }
    }
}