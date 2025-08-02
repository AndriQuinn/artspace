package com.example.artspace

import com.example.artspace.structure.DateConversion
import org.junit.Assert.assertEquals
import org.junit.Test

class DateConversionTest {

    @Test
    fun testDate() {

        val expectedDates = arrayOf(
            "03/14/2007",  // Mar 14, 2007 (Yuuka Hayase Birthday)
            "06/06/2008",  // Jun 06, 2008 (Rio Tsukatsuki Birthday)
            "12/25/2025",  // Dec 25, 2025 (Christmas Day)
            "02/14/2026",   // Feb 14, 2026 (Valentine's Day)
            "06/12/1896",  // Jun 12, 1896 (P.H. Independence Day)
            "12/30/1892",  // Dec 30, 1896 (Jose Rizal Death)
        )

        val actualDates = arrayOf(
            "Mar 14, 2007",  // 03/14/2007 (Yuuka Hayase Birthday)
            "Jun 06, 2008",  // 06/06/2008 (Rio Tsukatsuki Birthday)
            "Dec 25, 2025",  // 12/25/2025 (Christmas Day)
            "Feb 14, 2026",   // 02/14/2026 (Valentine's Day)
            "Jun 12, 1896",  // 06/12/1896  (P.H. Independence Day)
            "Dec 30, 1892",  // 12/30/1892 (Jose Rizal Death)
        )

        for (i in 0..(expectedDates.size-1)) {
            assertEquals(DateConversion.middleEndianToDateFormat(expectedDates[i]),actualDates[i])
        }
    }
}