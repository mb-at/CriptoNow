package com.example.criptonow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Hola GitHub, esto es un comentario de prueba.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Analytics event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de FireBase completa")
        analytics.logEvent("InitScreen", bundle)

    }
}