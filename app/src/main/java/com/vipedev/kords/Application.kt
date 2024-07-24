package com.vipedev.kords

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class KordsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Activer la persistance hors ligne pour Firebase Realtime Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
