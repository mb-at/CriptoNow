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
import androidx.fragment.app.Fragment
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

        //Instanciamos los fragments para poder operar con ellos
        var badgesFragment = BadgesFragment()
        var profileFragment = ProfileFragment()
        var criptoNowFragment = CriptoNowFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){

                R.id.badges -> {
                    setCurrentFragment(badgesFragment)
                    true
                }
                R.id.profile -> {
                    setCurrentFragment(profileFragment)
                    true
                }
                R.id.criptonow -> {
                    setCurrentFragment(criptoNowFragment)
                    true
                }
                R.id.logout -> {
                    //Borramos las credenciales guardadas de la sesión.
                    val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                    prefs.clear()
                    prefs.apply()

                    //Preguntamos al usuario si de verdad quiere cerrar sesión
                    logoutDialog()
                    true
                }

                else -> false
            }

        }

    }

    private fun setCurrentFragment(fragment: Fragment){
        /*Reemplaza el fragment indicado en función de la zona del bottom navigation
        * que hayamos seleccionado.*/

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()
        }
    }

    private fun getData(){

        try {

            val sqlQuery = "SELECT pregunta FROM CRIPTOPREGUNTAS"
            db?.FireQuery(sqlQuery)?.use {

                if(it.count > 0){

                    do{

                        val col = it.getColumnIndex("pregunta")
                        val values = it.getString(col)
                        Log.d(TAG,"Preguntas de criptnow: $values")
                        //pruebaBD.setText(values)

                    }while (it.moveToNext())
                }

            }

        }catch (e:Exception){

            e.printStackTrace()
        }
    }

    private fun logoutDialog(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Seguro que quieres cerrar sesión?")
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            //Cerramos sesión.
            FirebaseAuth.getInstance().signOut()

            //Este método vuelve a la pantalla anterior
            onBackPressed()
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