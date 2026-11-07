package com.money.desk.app

import android.app.Application
import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}