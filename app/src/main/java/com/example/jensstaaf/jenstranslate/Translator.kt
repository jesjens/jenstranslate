package com.example.jensstaaf.jenstranslate

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import kotlinx.coroutines.experimental.launch

class Translator(private val apiKey: String) {

    fun translate(textToTranslate: String, targetLanguage: String, fn: (String) -> Unit) {
        translate(textToTranslate, targetLanguage, object : TranslateCallback {
            override fun onSuccess(translatedText: String) {
                fn(translatedText)
            }

            override fun onFailure() {}
        }, fn)
    }

    private fun translate(textToTranslate: String, targetLanguage: String,
                          callback: TranslateCallback, fn: (String) -> Unit) {
        launch {
            try {
                val options = TranslateOptions.newBuilder()
                        .setApiKey(apiKey).build()
                val trService = options.service;
                val translation = trService.translate(textToTranslate, Translate.TranslateOption.targetLanguage(targetLanguage))

                callback.onSuccess(translation.translatedText);
            } catch (e: Exception) {
                fn(e.message!!)
                callback.onFailure()
            }
        }
    }
}

