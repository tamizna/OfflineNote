package com.tamizna.offlinenote.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.databinding.ActivityUserSettingBinding
import com.tamizna.offlinenote.sharedPreferences.SharedPreferencesHelper

class UserSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSettingBinding
    private val prefs = SharedPreferencesHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.btnSetPin.setOnClickListener {
            if (binding.edtInputPin.text.toString().length < 4) {
                Toast.makeText(this, "PIN harus 4 karakter", Toast.LENGTH_SHORT).show()
            } else {
                prefs.setPin(binding.edtInputPin.text.toString())
                Toast.makeText(this, "Pin berhasil diset", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}