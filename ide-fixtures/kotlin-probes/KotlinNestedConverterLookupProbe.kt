package io.github.nchaugen.tabletest.junit.converting

import io.github.nchaugen.tabletest.junit.TableTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.platform.commons.support.conversion.ConversionException
import org.tabletest.junit.TypeConverter
import java.time.LocalDate

open class KotlinNestedConverterLookupProbe {

    @TableTest(
        """
        Int | List | Set  | AVS  | Map         | Nested
        16  | [16] | {16} | {16} | [value: 16] | [value: [16, 16]]
            """
    )
    fun shouldResolveConverterInContainingClass(
        fromInt: Int,
        inList: java.util.List<Int>,
        inSet: Set<Int>,
        fromValueSet: Int,
        inMap: java.util.Map<String, Int>,
        inNested: java.util.Map<String, List<Int>>
    ) {
        val expected = 16
        assertEquals(expected, fromInt)
        assertEquals(listOf(expected), inList)
        assertEquals(setOf(expected), inSet)
        assertEquals(expected, fromValueSet)
        assertEquals(mapOf("value" to expected), inMap)
        assertEquals(mapOf("value" to listOf(expected, expected)), inNested)
    }

    @Suppress("unused")
    fun parseAge(age: String): Integer {
        throw ConversionException("Factory method not static, should not be called")
    }

    companion object {
        @JvmStatic
        @TypeConverter
        fun parseAge(age: Int): Int = age

        @JvmStatic
        @TypeConverter
        private fun parseAge(age: Long): Int {
            throw ConversionException("Factory method not accessible, should not be called")
        }

        @JvmStatic
        @TypeConverter
        fun parseAges(ages: Map<String, List<Int>>): List<Int> = parseAges(ages)
    }

    @TableTest(
        """
        Ages
        [ages: [16, 16]]
        """
    )
    fun shouldDemonstrateDelegatingFactoryMethodLookup(ages: List<Int>) {
        val expected = 16
        assertEquals(listOf(listOf(expected, expected)), ages)
    }

    @TableTest(
        """
        This Date  | Other Date | Is Before?
        today      | tomorrow   | true
        today      | yesterday  | false
        2024-02-29 | 2024-03-01 | true
            """
    )
    fun shouldOverrideFallbackLocalDateConversion(thisDate: LocalDate, otherDate: LocalDate?, expectedIsBefore: Boolean) {
        assertEquals(expectedIsBefore, thisDate.isBefore(otherDate))
    }

    @Nested
    inner class NestedClassConverterLookupProbe {

        @TableTest(
            """
            This Date  | Other Date | Is Before?
            today      | tomorrow   | true
            today      | yesterday  | false
            2024-02-29 | 2024-03-01 | true
            """
        )
        fun shouldResolveConverterFromNestedClass(
            thisDate: LocalDate,
            otherDate: LocalDate?,
            expectedIsBefore: Boolean
        ) {
            assertEquals(expectedIsBefore, thisDate.isBefore(otherDate))
        }

        @Nested
        inner class DeeplyNestedClassConverterLookupProbe {

            @TableTest(
                """
                This Date  | Other Date | Is Before?
                today      | tomorrow   | true
                today      | yesterday  | false
                2024-02-29 | 2024-03-01 | true
                """
            )
            fun shouldResolveConverterFromDeeplyNestedClass(
                thisDate: LocalDate,
                otherDate: LocalDate?,
                expectedIsBefore: Boolean
            ) {
                assertEquals(expectedIsBefore, thisDate.isBefore(otherDate))
            }

            @TableTest(
                """
                Left token | Middle token | Right token
                alpha      | beta gamma   | delta
                1          | 1 1          | 1
                    """
            )
        }
    }

}

@TypeConverter
fun parseRelativeLocalDateKeyword(input: String): LocalDate {
    return when (input) {
        "yesterday" -> LocalDate.parse("2025-06-06")
        "today" -> LocalDate.parse("2025-06-07")
        "tomorrow" -> LocalDate.parse("2025-06-08")
        else -> LocalDate.parse(input)
    }
}
