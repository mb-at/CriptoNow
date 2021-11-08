package com.example.criptonow

import android.app.Notification
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.QuickViewConstants
import android.content.res.Configuration
import android.os.BadParcelableException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.Exception

/*Esta clase va  a ser el inicio de la aplicación. Aparecerá en pantalla una vez que el usuario
* ya registrado ha hecho login en la aplicación.*/

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity(){

    //Array de eleción cuando pulsamos el botón para aprender sobre criptomonedas
    val itemList = arrayOf("Bitcoin","Altcoins")
    private var db: CriptoNowDB ?= null
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        db = CriptoNowDB(this)
        getData()

        //SetUp
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        //setUp(email ?: "", provider ?: "")

        //Persistimos las credenciales del usuario para que se mantenga activa su sesión
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()

        //Añadimos escuchador al botón de criptomonedas para mostrar el diálogo
        /*criptoassetsButton.setOnClickListener{

            criptoAssetsDialog()
        }
        profileButton.setOnClickListener{

            startActivity(Intent(this, ProfileActivity::class.java))
        }

        badgesButton.setOnClickListener{

            startActivity(Intent(this, BadgesActivity::class.java))
        }*/

    }

    private fun getData(){

        try {

            val sqlQuery = "SELECT Address FROM TABLE1"
            db?.FireQuery(sqlQuery)?.use {

                if(it.count > 0){

                    do{

                        val col = it.getColumnIndex("Address")
                        val values = it.getString(col)
                        Log.d(TAG,"Direcciones: $values")
                        //pruebaBD.setText(values)

                    }while (it.moveToNext())
                }

            }

        }catch (e:Exception){

            e.printStackTrace()
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

    /*private fun setUp(email:String, provider: String){

            //emailTextView.text = email
            //providerTextView.text = provider

            //Damos la funcionalidad al botón encargado de cerrar sesión.
           /* logOutButton.setOnClickListener{

                //Borramos las credenciales guardadas de la sesión.
                val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()

                //Cerramos sesión.
                FirebaseAuth.getInstance().signOut()

                //Este método vuelve a la pantalla anterior
                onBackPressed()

            }*/

    }*/


}