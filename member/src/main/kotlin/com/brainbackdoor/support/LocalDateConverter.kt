package com.brainbackdoor.support

import org.springframework.core.convert.converter.Converter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter : Converter<String, LocalDate> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)

    override fun convert(source: String): LocalDate? {
        return if (source.isEmpty()) {
            null
        } else LocalDate.parse(source, formatter)
    }

    companion object {
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    }
}
