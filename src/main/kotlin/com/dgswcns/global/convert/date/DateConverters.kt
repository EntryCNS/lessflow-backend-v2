package com.dgswcns.global.convert.date

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.text.SimpleDateFormat
import java.util.Date

@WritingConverter
class DateWritingConvert : Converter<Date, String>{
    override fun convert(source: Date): String? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(source)
    }
}
@ReadingConverter
class DateReadingConvert : Converter<String, Date> {
    override fun convert(source: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(source);
    }
}
