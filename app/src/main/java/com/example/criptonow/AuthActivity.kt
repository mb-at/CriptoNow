package com.example.criptonow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    //Referenciamos los componentes del activityAuth
    val signUpButton = findViewById<Button>(R.id.signUpButton)
    val logInButton = findViewById<Button>(R.id.logInButton)
    val emailEditText = findViewById<EditText>(R.id.emailEditText)
    val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //setup
        setup()

    }

    private fun setup(){

        title = "Autenticación"

        //Establecemos escuchador del botón de registro
        signUpButton.setOnClickListener{

            //Minuto 21:18 del vídeo
        }
    }
}