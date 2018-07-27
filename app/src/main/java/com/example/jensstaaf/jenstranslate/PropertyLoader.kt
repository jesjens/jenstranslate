package com.example.jensstaaf.jenstranslate

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.IOException
import java.util.*

class PropertyLoader(private val context: Context) {

    private val TAG = "PropertyLoader"

    fun getConfigValue(name: String): String? {
        val resources = context.resources

        try {
            val rawResource = resources.openRawResource(R.raw.config)
            val properties = Properties()
            properties.load(rawResource)
            return properties.getProperty(name)
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Unable to find the config file: " + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to open config file.")
        }

        return null
    }
}