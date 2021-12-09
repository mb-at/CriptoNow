package com.example.criptonow

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

/*Esta clase va  a ser el inicio de la aplicación. Aparecerá en pantalla una vez que el usuario
* ya registrado ha hecho login en la aplicación.*/

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity(){
    //Array de eleción cuando pulsamos el botón para aprender sobre criptomonedas

    private val TAG = "HomeActivity"
    private var db: CriptoNowDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        /*-------------------------------------------------------------------------------------------------------------
        DESCOMENTAMOS ESTAS LÍNEAS DE ABAJO PARA PODER GENERAR EL ARCHIVO CON LAS 25 PISTAS INICIALES EN EMULADOR NUEVO
        Y LUEGO LAS VOLVEMOS A COMENTAR
        TODOlATER:Encontrar manera de incluir esos archivos desde el principio
       ----------------------------------------------------------------------------------------------------------------
        */

        //Obtenemos el preferenceManager para guardar el número de pistas que va a tener el usuario, empieza con 25
        /*val clues = getSharedPreferences(getString(R.string.cluesNumber),Context.MODE_PRIVATE)
        val editor = clues.edit()
        editor?.apply {

            //Guardamos el número de pistas del usuario
            putInt("numPistas", 25)
        }?.apply()*/

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
        val badgesFragment = BadgesFragment()
        val profileFragment = ProfileFragment()
        val criptoNowFragment = CriptoNowFragment()

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
            replace(R.id.appfragments, fragment)
            commit()
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