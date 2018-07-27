package com.example.jensstaaf.jenstranslate

import java.util.Locale
import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import android.view.View
import android.widget.ArrayAdapter
import com.example.jensstaaf.jenstranslate.model.LanguageItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var translator: Translator
    private lateinit var speaker: Speaker
    private val translatedTextLiveData = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()

    @Inject lateinit var propertyLoader: PropertyLoader

    private val REQ_CODE_SPEECH_INPUT = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speaker = Speaker(this)

        val outputLanguages = listOf(
                LanguageItem("English", "ENG", Locale.ENGLISH),
                LanguageItem("German", "GER", Locale.GERMAN),
                LanguageItem("France", "FR", Locale.FRANCE),
                LanguageItem("Italian", "ITA", Locale.ITALIAN),
                LanguageItem("Chinese", "zh-CN", Locale.CHINA),
                LanguageItem("Japanese", "JA", Locale.JAPAN),
                LanguageItem("Korean", "KO", Locale.KOREA))

        outputLangSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, outputLanguages)

        translatedTextLiveData.observe(this, Observer<String> {  string ->
            txtTranslatedText.text = string
        })

        isLoading.observe(this, Observer<Boolean> { isVisible ->
            progressBar.visibility = when (isVisible) {
                true -> View.VISIBLE
                else -> View.INVISIBLE
            }

            btnSpeak.isEnabled = !isVisible!!
        })

        /*val propertyLoader = DaggerTalkListComponent.builder()
                .appModule(AppModule(this))
                .build().propertyLoader*/

        val apiKey = propertyLoader.getConfigValue("api_key")!!
        translator = Translator(apiKey)

        btnSpeak.setOnClickListener { promptSpeechInput() }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt))
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedLanguage = outputLangSpinner.selectedItem as LanguageItem

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    isLoading.postValue(true)

                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val spokenText = result[0]
                    translator.translate(spokenText, selectedLanguage.short) { translatedText ->
                        translatedTextLiveData.postValue(translatedText)
                        speaker.speakText(translatedText, selectedLanguage.locale)
                        isLoading.postValue(false)
                    }
                }
            }
        }
    }
}