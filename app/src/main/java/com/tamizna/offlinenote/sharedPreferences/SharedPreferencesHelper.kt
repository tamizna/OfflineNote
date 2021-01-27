package com.tamizna.offlinenote.sharedPreferences

import android.content.Context
import com.tamizna.offlinenote.CustomApplication

class SharedPreferencesHelper {

    companion object {
        const val SP_NAME = "note-prefs"
        const val PIN = "PREF_PIN"
    }

    private val prefs = CustomApplication.application.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun getPin() : String? = prefs.getString(PIN, null)

    fun setPin(pin : String) {
        prefs.edit().putString(PIN, pin).apply()
    }
}