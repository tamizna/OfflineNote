package com.tamizna.offlinenote.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.databinding.ActivityLoginBinding
import com.tamizna.offlinenote.sharedPreferences.SharedPreferencesHelper
import com.tamizna.offlinenote.ui.folder.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val prefs = SharedPreferencesHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtInputPin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                binding.btnLogin.isEnabled = binding.edtInputPin.text?.trim()?.length!! >= 4
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnLogin.isEnabled = binding.edtInputPin.text?.trim()?.length!! >= 4
            }
        })

        initListener()
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            if(binding.edtInputPin.text.toString() == prefs.getPin()) {
             startActivity(Intent(this, MainActivity::class.java))
             finish()
            } else {
                Toast.makeText(this, "Pin salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}