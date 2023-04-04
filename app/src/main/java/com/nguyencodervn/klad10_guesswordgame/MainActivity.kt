package com.nguyencodervn.klad10_guesswordgame

import android.os.Bundle
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
    private lateinit var storedLang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Note Load before setContentView
        loadLang()
        setContentView(R.layout.activity_main)
        materialToolbar = findViewById(R.id.materialToolbar)
        setSupportActionBar(materialToolbar)
    }

    @ExperimentalBadgeUtils
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
            enBadge, materialToolbar,
            R.id.englishMn
        )

        BadgeUtils.attachBadgeDrawable(
            vnBadge, materialToolbar,
            R.id.vietnamMn
        )

        if (storedLang == "en") enBadge.isVisible = true
        else vnBadge.isVisible = true

        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.englishMn -> {
                enBadge.isVisible = true
                vnBadge.isVisible = false
                setLang("en")
                recreate()
            }
            R.id.vietnamMn -> {
                enBadge.isVisible = false
                vnBadge.isVisible = true
                setLang("vi")
                recreate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLang(lang: String) {
        val appCompat = LocaleListCompat.forLanguageTags(lang)
        AppCompatDelegate.setApplicationLocales(appCompat)

        val sharedPref = getSharedPreferences("Setting", MODE_PRIVATE)
        sharedPref.edit().putString("Lang", lang).apply()
    }

    private fun loadLang() {
        val sharedPref = getSharedPreferences("Setting", MODE_PRIVATE)
        val lang = sharedPref.getString("Lang", "") ?: ""
        storedLang = lang
        setLang(lang)
    }
}