package com.example.jensstaaf.jenstranslate

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class Speaker(context: Context) : TextToSpeech.OnInitListener {
    override fun onInit(p0: Int) {}

    private val speaker: TextToSpeech = TextToSpeech(context, this)

    fun speakText(text: String, locale: Locale) {
        val result = speaker!!.setLanguage(locale)

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS","The Language specified is not supported!")
        } else {
            speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
}