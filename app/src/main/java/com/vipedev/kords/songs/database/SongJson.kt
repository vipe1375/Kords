package com.vipedev.kords.songs.database

import org.json.JSONArray
import org.json.JSONObject

object SongJson {
    fun structureToObj(map: Map<String, List<String>>) = JSONObject().apply {
        map.forEach { (type, chords) -> put(type, JSONArray(chords)) }
    }

    fun structureFromObj(obj: JSONObject): Map<String, List<String>> {
        val result = LinkedHashMap<String, List<String>>()
        obj.keys().forEach { key ->
            val arr = obj.getJSONArray(key)
            result[key] = List(arr.length()) { arr.getString(it) }
        }
        return result
    }

    fun structureToJson(map: Map<String, List<String>>): String = structureToObj(map).toString()
    fun structureFromJson(json: String): Map<String, List<String>> = structureFromObj(JSONObject(json))
}