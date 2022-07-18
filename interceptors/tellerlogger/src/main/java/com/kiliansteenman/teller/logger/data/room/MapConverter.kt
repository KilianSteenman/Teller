package com.kiliansteenman.teller.logger.data.room

import androidx.room.TypeConverter

internal class MapConverter {

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        if (value.isBlank()) return emptyMap()

        return value.split(PARAM_SPLIT)
            .filter { it.isNotBlank() }
            .mapNotNull {
                val keyValue = it.split(KEY_VALUE_SPLIT)
                if (keyValue.size == 2) {
                    Pair(keyValue[0], keyValue[1])
                } else null
            }
            .associate { it }
    }

    @TypeConverter
    fun fromMap(value: Map<String, String>): String =
        value.map { (key, value) ->
            "$key$KEY_VALUE_SPLIT$value"
        }.joinToString(PARAM_SPLIT)

    private companion object {
        private const val KEY_VALUE_SPLIT = "<==>"
        private const val PARAM_SPLIT = "<;;>"
    }
}