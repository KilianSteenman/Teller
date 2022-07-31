package com.kiliansteenman.teller.logger.data.room

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MapConverterTest {

    private val mapConverter = MapConverter()

    private companion object {
        private const val KEY_VALUE_SPLIT = "<==>"
        private const val PARAM_SPLIT = "<;;>"
    }

    @Nested
    inner class ToMapTest {

        @Test
        fun `when value is empty, then empty map is returned`() {
            assertEquals(0, mapConverter.toMap("").size)
        }

        @Test
        fun `when value is blank, then empty map is returned`() {
            assertEquals(0, mapConverter.toMap(" ").size)
        }

        @Test
        fun `when value is just a separator, then empty map is returned`() {
            assertEquals(0, mapConverter.toMap(PARAM_SPLIT).size)
        }

        @Test
        fun `when the string contains a value but without key, then key is blank`() {
            val map = mapConverter.toMap("${KEY_VALUE_SPLIT}value")

            assertEquals(1, map.size)
            assertEquals("value", map[""])
        }

        @Test
        fun `when the string contains a single key value pair, then map contains a single item`() {
            val map = mapConverter.toMap("key${KEY_VALUE_SPLIT}value")

            assertEquals(1, map.size)
            assertEquals("value", map["key"])
        }

        @Test
        fun `when the string contains multiple key value pairs, then map contains all items`() {
            val map = mapConverter.toMap(
                "key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}other${KEY_VALUE_SPLIT}thing"
            )

            assertEquals(2, map.size)
            assertEquals("value", map["key"])
            assertEquals("thing", map["other"])
        }

        @Test
        fun `when the string ends with a separator, invalid data is filtered`() {
            val map = mapConverter.toMap("key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}")

            assertEquals(1, map.size)
            assertEquals("value", map["key"])
        }

        @Test
        fun `when the string ends with a key without a key value separator, invalid data is filtered`() {
            val map = mapConverter.toMap("key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}other")

            assertEquals(1, map.size)
            assertEquals("value", map["key"])
        }

        @Test
        fun `when the string ends with a key without a value, then value is blank`() {
            val map = mapConverter.toMap(
                "key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}other${KEY_VALUE_SPLIT}"
            )

            assertEquals(2, map.size)
            assertEquals("value", map["key"])
            assertEquals("", map["other"])
        }
    }

    @Nested
    inner class FromMapTest {

        @Test
        fun `empty map is converted to an empty String`() {
            assertEquals("", mapConverter.fromMap(emptyMap()))
        }

        @Test
        fun `map with values is converted to a String`() {
            val value = mapConverter.fromMap(
                mapOf(
                    "key" to "value",
                    "space" to "a space"
                )
            )

            assertEquals(
                "key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}space${KEY_VALUE_SPLIT}a space",
                value
            )
        }

        @Test
        fun `map with blank value is converted to a String`() {
            val value = mapConverter.fromMap(
                mapOf(
                    "key" to "value",
                    "empty" to "",
                    "other" to "thing",
                )
            )

            assertEquals(
                "key${KEY_VALUE_SPLIT}value${PARAM_SPLIT}empty${KEY_VALUE_SPLIT}${PARAM_SPLIT}other${KEY_VALUE_SPLIT}thing",
                value
            )
        }
    }

    @Nested
    inner class FromToTest {

        @Test
        fun `map is the same when converted to String and back`() {
            val map = mapOf(
                "key" to "value",
                "empty" to "",
                "other" to "thing",
            )

            val stringValue = mapConverter.fromMap(map)

            assertEquals(
                map,
                mapConverter.toMap(stringValue)
            )
        }
    }
}