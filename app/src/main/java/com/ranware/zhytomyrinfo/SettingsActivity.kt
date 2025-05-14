package com.ranware.zhytomyrinfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupBottomNav()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        })
    }

    private fun setupBottomNav() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setBackgroundColor(0xFFEEEEEE.toInt())
        bottomNav.setItemIconTintList(null)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_settings -> {
                    true // already here
                }
                R.id.nav_archive -> {
                    startActivity(Intent(this, ArchiveActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}