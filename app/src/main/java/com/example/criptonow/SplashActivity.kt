package com.example.criptonow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    //Instanciamos nuestra base de datos
    private var db:CriptoNowDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = CriptoNowDB(this)

        //Abrimos la base de datos
        db?.openDatabase()

        //Una vez que llega a esta activity, inicia la en mainActivity
        startActivity(Intent(this, AuthActivity::class.java))

        //Evita que al navegar hacia atrás se siga mostrando la splash screen.
        finish()
    }
}