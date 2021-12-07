package com.example.criptonow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import kotlinx.android.synthetic.main.fragment_cripto_now.*

class CriptoNowFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cripto_now, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preguntados = PreguntadosFragment()

        //Listeners del fragment.
        preguntadosBlockChain.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "blockchain")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }
        }

        preguntadosCriptoActivos.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "criptoactivos")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }
        }

        preguntadosNfts.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "nfts")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }
        }

        //ESTABLECEMOS LOS ESCUCHADORES DE LAS OPCIONES
        opcionesBlockChain.setOnClickListener{

            //Instanciamos un array con las opciones que le damos al usuario
            val options = arrayOf("ÚLTIMAS NOTICIAS","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val blockchainOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de blockchain")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS"){

                            //Vamos a Binance con referencia
                            Toast.makeText(context, "Vamos a noticias", Toast.LENGTH_SHORT).show()
                        }
                        else{

                            //Vamos a coinbase con referencia
                            Toast.makeText(context, "Vamos a recomendaciones de inversión", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            blockchainOptions.show()
        }

        opcionesCriptoActivos.setOnClickListener{

            //Instanciamos un array con las opciones que le damos al usuario
            val options = arrayOf("ÚLTIMAS NOTICIAS","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val criptoOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de criptoactivos")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS"){

                            //Vamos a Binance con referencia
                            Toast.makeText(context, "Vamos a noticias", Toast.LENGTH_SHORT).show()
                        }
                        else{

                            //Vamos a coinbase con referencia
                            Toast.makeText(context, "Vamos a recomendaciones de inversión", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            criptoOptions.show()
        }

        opcionesNfts.setOnClickListener{

            //Instanciamos un array con las opciones que le damos al usuario
            val options = arrayOf("ÚLTIMAS NOTICIAS","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val nftOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de Nfts")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS"){

                            //Vamos a Binance con referencia
                            Toast.makeText(context, "Vamos a noticias", Toast.LENGTH_SHORT).show()
                        }
                        else{

                            //Vamos a coinbase con referencia
                            Toast.makeText(context, "Vamos a recomendaciones de inversión", Toast.LENGTH_SHORT).show()
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            nftOptions.show()
        }

        //Ponemos a la escucha a los botones de errores
        erroresBlockchain.setOnClickListener {


        }

        erroresCriptoactivos.setOnClickListener {


        }


        erroresNfts.setOnClickListener {


        }
    }

}