package com.money.desk.app

import android.app.Application
import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initEmojiCompat()
    }

    /**
     * Инициализация EmojiCompat с шрифтом, вшитым в APK (emoji2-bundled).
     *
     * Подключение emoji2-bundled отключает автоматический EmojiCompatInitializer
     * (androidx.startup), поэтому вызывать EmojiCompat.init() нужно вручную и как можно раньше,
     * иначе EmojiCompat.get() выбросит исключение.
     * Шрифт загружается асинхронно на переданном executor. Конструктор
     */
    private fun initEmojiCompat() {
        EmojiCompat.init(
            BundledEmojiCompatConfig(this, Dispatchers.Default.asExecutor())
        )
    }
}
