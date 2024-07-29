package com.vipedev.kords.settings_screen

class ToggleOptionItem(
    val title: String,
    val id: Int,
    val description_on: String,
    val description_off: String,
    val enabled: Boolean,
    //val key: String

)

class ListOptionItem(
    val title: String,
    val description: String,
    val enabled: Boolean,
    val items: List<LanguageItem>
    //val key: String
)

class LanguageItem (
    val title: String,
    val id: String
)
