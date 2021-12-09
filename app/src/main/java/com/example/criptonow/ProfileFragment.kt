package com.example.criptonow

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.criptonow.R
import kotlinx.android.synthetic.main.fragment_cripto_now.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    private val TAG = "ProfileFragment"
    private val nameK = "name"
    private val emailK = "email"
    private val criptoK = "cripto"
    private val exchangeK = "exchange"
    private val yearK = "year"
    private val whyK = "why"
    private val badgesK = "badges"
    private val cluesK = "numPistas"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Cargamos la información guardada en el perfil
        loadProfileData()

        //Instanciamos un array con las opciones que le damos al usuario
        val options = arrayOf("Registrarme en Binance","Registrarme en CoinBase")

        //Creamos el diálogo de opciones para el perfil
        val profileOptions = AlertDialog.Builder(context)
                .setTitle("Ábrete una cuenta en exchange")
                .setSingleChoiceItems(options, 0) {_, i ->

                    if (options[i] == "Registrarme en Binance"){

                        //Vamos a Binance con referencia
                        Toast.makeText(context, "Binance", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        //Vamos a coinbase con referencia
                        Toast.makeText(context, "Coinbase", Toast.LENGTH_SHORT).show()
                    }

                }
                .setNegativeButton("Back") {dialog, _ ->

                    dialog.dismiss()

                }.create()

        opcionesProfile.setOnClickListener{

            //Mostramos el diálogo con la opciones al usuario
            profileOptions.show()
        }

        //lÓGICA QUE GUARDA LOS DATOS DEL PERFIL PARA MOSTRARLOS POSTERIORMENTE
        saveProfile.setOnClickListener {

            //Recogemos la información que el usuario ha introducido en el editText
            val name = profileName.text.toString()
            val cripto = profileCripto.text.toString()
            val exchange = profileExchange.text.toString()
            val year = profileYear.text.toString()
            val why = profileWhy.text.toString()

            //Obtenemos el preferenceManager
            val prefs = activity?.getSharedPreferences(getString(R.string.profile_data),Context.MODE_PRIVATE)
            val editor = prefs?.edit()
            editor?.apply {

                //Guardamos todos los valores que el usuario indica en el archivo de preferencias
                putString(nameK,name)
                putString(criptoK, cripto)
                putString(exchangeK, exchange)
                putString(yearK,year)
                putString(whyK,why)
                putInt(badgesK, 0)
            }?.apply()

            Toast.makeText(activity, "Los datos han sigo guardados", Toast.LENGTH_LONG).show()

        }

    }

    private fun loadProfileData(){

        //Obtenemos los dos archivos de preferencias que generamos previamente
        val profilePrefs = activity?.getSharedPreferences(getString(R.string.profile_data),Context.MODE_PRIVATE)
        val userCredentials = activity?.getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE)
        val cluesPref = requireActivity().getSharedPreferences(getString(R.string.cluesNumber),Context.MODE_PRIVATE)

        //Recuperamos los valores de esos archivos
        val name = profilePrefs?.getString(nameK,null)
        val cripto = profilePrefs?.getString(criptoK,null)
        val exchange = profilePrefs?.getString(exchangeK,null)
        val year = profilePrefs?.getString(yearK,null)
        val why = profilePrefs?.getString(whyK,null)
        val email = userCredentials?.getString(emailK,null)
        val badgesNumber = profilePrefs?.getInt(badgesK,0)
        val cluesNumber = cluesPref?.getInt(cluesK, 0)

        //Los establecemos en el perfil del usuario cuando recupere la sesión
        profileBigName.text = name
        profileEmail.text = email
        badges.text = badgesNumber.toString()
        profileName.setText(name)
        profileCripto.setText(cripto)
        profileExchange.setText(exchange)
        profileYear.setText(year)
        profileWhy.setText(why)
        clues.setText(cluesNumber.toString())

    }

}