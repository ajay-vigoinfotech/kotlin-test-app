package com.example.replicaapp.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.replicaapp.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    protected var isDarkTheme: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
    }

    private fun applyTheme() {
        prefs = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        isDarkTheme = prefs.getBoolean("is_dark", false)
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme_Light)
        }
    }

    protected fun toggleThemeAndRecreate() {
        isDarkTheme = !isDarkTheme
        prefs.edit().putBoolean("is_dark", isDarkTheme).apply()
        recreate()
    }
}