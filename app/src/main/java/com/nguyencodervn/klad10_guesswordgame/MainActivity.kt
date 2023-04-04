package com.nguyencodervn.klad10_guesswordgame

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils


class MainActivity : AppCompatActivity() {
    private lateinit var materialToolbar: MaterialToolbar
    private lateinit var enBadge: BadgeDrawable
    private lateinit var vnBadge: BadgeDrawable
    private lateinit var defaultLanguage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        Log.i("MYTAG", "onCreate: ")
        setContentView(R.layout.activity_main)
        materialToolbar = findViewById(R.id.materialToolbar)
        setSupportActionBar(materialToolbar)
    }


    @ExperimentalBadgeUtils
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        initBadges()
        if (defaultLanguage == "en") enBadge.isVisible =
            true else vnBadge.isVisible = true

        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalBadgeUtils
    private fun initBadges() {
        val blue300 = ContextCompat.getColor(baseContext, R.color.blue_300)
        enBadge = BadgeDrawable.create(this).apply {
            isVisible = false
            backgroundColor = blue300
        }
        vnBadge = BadgeDrawable.create(this).apply {
            isVisible = false
            backgroundColor = blue300
        }

        BadgeUtils.attachBadgeDrawable(
            enBadge,
            materialToolbar, R.id.englishMn
        )

        BadgeUtils.attachBadgeDrawable(
            vnBadge,
            materialToolbar, R.id.vietnamMn
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.englishMn -> {
                enBadge.isVisible = true
                vnBadge.isVisible = false
                setLocale("en")
                recreate()
            }
            R.id.vietnamMn -> {
                enBadge.isVisible = false
                vnBadge.isVisible = true
                setLocale("vi")
                recreate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLocale(lang: String) {

//        val locale = Locale(lang)
//        Locale.setDefault(locale)
//        val config = resources.configuration
//        config.setLocale(locale)
//
//        baseContext.resources.updateConfiguration(
//            config, baseContext.resources.displayMetrics
//        )

        val appLocale = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val shared = getSharedPreferences("Setting", MODE_PRIVATE)
        shared.edit().putString("Lang", lang).apply()
    }

    private fun loadLocale() {
        val shared = getSharedPreferences("Setting", MODE_PRIVATE)
        val lang = shared.getString("Lang", "") ?: ""
        defaultLanguage = lang
        setLocale(lang)
    }
}