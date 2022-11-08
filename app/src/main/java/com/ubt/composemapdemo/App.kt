package com.ubt.composemapdemo

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        setApp(this)
    }

    companion object {
        private lateinit var app: App

        fun setApp(app: App) {
            this.app = app
        }

        fun getApp(): App {
            return app
        }
    }
}