package com.example.artspace.structure

class DateConversion {
    companion object {
        @JvmStatic
        internal fun middleEndianToDateFormat(date: String): String {
            val dateFormat = date.split("/").toMutableList()
            dateFormat[0] = convertMMToMonth(dateFormat[0])
            dateFormat[1] = "${dateFormat[1]},"
            return dateFormat.joinToString(" ")

        }

        @JvmStatic
        internal fun convertMMToMonth(month: String) = when (month.toInt()) {
            1 ->  "Jan"
            2 ->  "Feb"
            3 ->  "Mar"
            4 ->  "Apr"
            5 ->  "May"
            6 ->  "Jun"
            7 ->  "Jul"
            8 ->  "Aug"
            9 ->  "Sep"
            10 ->  "Oct"
            11 ->  "Nov"
            12 ->  "Dec"
            else ->  ""
        }
    }
}