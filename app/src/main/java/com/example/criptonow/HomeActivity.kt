package com.example.criptonow

import android.content.Context
import android.content.DialogInterface
import android.content.QuickViewConstants
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

/*Esta clase va  a ser el inicio de la aplicación. Aparecerá en pantalla una vez que el usuario
* ya registrado ha hecho login en la aplicación.*/

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {

    //Array de eleción cuando pulsamos el botón para aprender sobre criptomonedas
    val itemList = arrayOf("Bitcoin","Altcoins")

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

        //Añadimos escuchador al botón de criptomonedas para mostrar el diálogo
        criptoassetsButton.setOnClickListener{

            criptoAssetsDialog()
        }

    }

    private fun setUp(email:String, provider: String){

            //emailTextView.text = email
            //providerTextView.text = provider

            //Damos la funcionalidad al botón encargado de cerrar sesión.
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

    private fun criptoAssetsDialog(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿De qué tema te gustaría aprender?")
        builder.setSingleChoiceItems(itemList, -1){ dialog, which ->

            //Mostramos la opción que hemos elegido por pantalla
            Toast.makeText(applicationContext, itemList[which], Toast.LENGTH_LONG).show()

        }

        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.setNegativeButton("Back", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        //Creamos nuestro diálogo con nuestras características
        val alertDialog = builder.create()
        alertDialog.show()

    }
}