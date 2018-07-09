package com.example.jensstaaf.jenstranslate

internal interface TranslateCallback {
    fun onSuccess(translatedText: String)
    fun onFailure()
}