package com.example.criptonow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Hola GitHub, esto es un comentario de prueba.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun saludar(){

        println("Hola GitHub");
    }
}