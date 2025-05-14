package com.ranware.zhytomyrinfo

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView


class ArchiveActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var bottomNav: BottomNavigationView
    private val baseUrl = "https://zhitomir.info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_archive)

        setupWebView()
        setupBottomNav()
        setupBackHandler()
    }

    private fun setupWebView() {
        webView = findViewById(R.id.archive_webview)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        webView.webViewClient = WebViewClient()
        webView.loadUrl(baseUrl)
        webView.post {
            webView.evaluateJavascript(
                "(function(){ var archive = document.querySelector('div.archive'); if(archive) document.body.innerHTML = archive.outerHTML; })();",
                null
            )
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
                    true // already here
                }
                else -> false
            }
        }
    }

    private fun setupBackHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack()
                else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}