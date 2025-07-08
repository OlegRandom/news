package com.ranware.zhytomyrinfo

import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var bottomNav: BottomNavigationView
    private val baseUrl = "https://zhitomir.info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupWebView()
        setupBottomNav()
        setupBackHandler()
    }

    private fun setupWebView() {
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                openLinkActivity(request.url.toString())
                return true
            }
        }
        webView.loadUrl(baseUrl)
    }

    private fun setupBottomNav() {
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setBackgroundColor(0xFFEEEEEE.toInt()) // светло-серый фон
        bottomNav.setItemIconTintList(null) // оригинальные цвета иконок
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    webView.loadUrl(baseUrl)
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
                    startActivity(Intent(this, ContactActivity::class.java))
                    true
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

    private fun openLinkActivity(url: String) {
        val intent = Intent(this, LinkActivity::class.java).apply {
            putExtra("link_url", url)
        }
        startActivity(intent)
    }
}