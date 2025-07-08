package com.ranware.zhytomyrinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContactActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        setupBottomNav()
        setupContactInfo()
        setupBackHandler()
    }

    private fun setupContactInfo() {
        val phoneText = findViewById<TextView>(R.id.phone_text)
        val emailText = findViewById<TextView>(R.id.email_text)
        
        // Set up phone number with click to dial
        phoneText.setOnClickListener {
            val phoneNumber = "+380412345678"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
        
        // Set up email with click to send email
        emailText.setOnClickListener {
            val email = "info@zhitomir.info"
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            startActivity(intent)
        }
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
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.nav_archive -> {
                    startActivity(Intent(this, ArchiveActivity::class.java))
                    true
                }
                R.id.nav_contact -> {
                    true // already here
                }
                else -> false
            }
        }
    }

    private fun setupBackHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        })
    }
}