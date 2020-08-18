package com.codose.travelmantics_andela.utils

import android.app.Application
import com.google.firebase.FirebaseApp

class TravelAppController : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}