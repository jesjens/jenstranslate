package com.example.jensstaaf.jenstranslate.model

import java.util.*

class LanguageItem(val name: String, val short: String, val locale: Locale) {
    override fun toString(): String {
        return name
    }
}