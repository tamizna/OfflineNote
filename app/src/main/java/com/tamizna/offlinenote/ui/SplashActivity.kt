package com.tamizna.offlinenote.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.sharedPreferences.SharedPreferencesHelper
import com.tamizna.offlinenote.ui.folder.MainActivity

class SplashActivity : AppCompatActivity() {

    private val prefs = SharedPreferencesHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(prefs.getPin().isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}