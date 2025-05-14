package com.ranware.zhytomyrinfo

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class LinkActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var bottomNav: BottomNavigationView
    private val homeUrl = "https://zhitomir.info"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        setupWebView()
        setupBottomNav()
        setupBackHandler()
    }

    private fun setupWebView() {
        webView = findViewById(R.id.link_webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
// Особые правила для новостей и постов: удаляем footer и блок price-of-products
                if (url.startsWith("$homeUrl/news_") || url.startsWith("$homeUrl/post_")) {
                    val js = """
                (function() {
                    var footer = document.querySelector('div.footer');
                    if (footer) footer.remove();
                    var priceBlock = document.querySelector('div.price-of-products');
                    if (priceBlock) priceBlock.remove();
                    var vb = document.querySelector('div.video-bottom.on-news');
                    if (vb) vb.remove();
                    var topLine = document.querySelector('.top-line');
                    if (topLine) topLine.remove();
                    var mainWrapper = document.querySelector('div.wrapper.main-block');
                    if (mainWrapper) mainWrapper.remove();
                    var wrapperMenu = document.querySelector('.wrapper-menu');
                    if (wrapperMenu) wrapperMenu.remove();
                })();
            """.trimIndent()
                    view.evaluateJavascript(js, null)
                }
            }
        }
        intent.getStringExtra("link_url")?.let { webView.loadUrl(it) }
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