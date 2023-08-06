package com.brainbackdoor.support

import org.springframework.core.convert.converter.Converter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter : Converter<String, LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)

    override fun convert(source: String): LocalDateTime? {
        return if (source.isEmpty()) {
            null
        } else LocalDateTime.parse(source, formatter)
    }

    companion object {
        const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}
