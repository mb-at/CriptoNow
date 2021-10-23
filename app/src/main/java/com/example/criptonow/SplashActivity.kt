package com.example.criptonow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Una vez que llega a esta activity, inicia la en mainActivity
        startActivity(Intent(this, AuthActivity::class.java))

        //Evita que al navegar hacia atrás se siga mostrando la splash screen.
        finish()
    }
}