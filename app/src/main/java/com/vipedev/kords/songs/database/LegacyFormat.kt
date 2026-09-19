package com.vipedev.kords.songs.database

object LegacyFormat {
    fun structureFromString(value: String?): Map<String, List<String>> {
        val list = value?.split(":")?.filter { it.isNotBlank() }
        val result: MutableMap<String, List<String>> = mutableMapOf()

        list?.forEach { item ->
            val structElt = item.split("-").filter { it.isNotBlank() }
            if (structElt.isNotEmpty()) {
                result[structElt[0]] = structElt.drop(1)
            }
        }
        return result
    }
}