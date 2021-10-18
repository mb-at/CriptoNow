package com.example.criptonow

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

/*Esta clase va  a ser el inicio de la aplicación. Aparecerá en pantalla una vez que el usuario
* ya registrado ha hecho login en la aplicación.*/

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //SetUp
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setUp(email ?: "", provider ?: "")

        //Persistimos las credenciales del usuario para que se mantenga activa su sesión
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()

    }

    private fun setUp(email:String, provider: String){

            emailTextView.text = email
            providerTextView.text = provider

            //Damos la funcionalidad para cerrar sesión.
            logOutButton.setOnClickListener{

                //Borramos las credenciales guardadas de la sesión.
                val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()

                //Cerramos sesión.
                FirebaseAuth.getInstance().signOut()

                //Este método vuelve a la pantalla anterior
                onBackPressed()

            }

    }
}