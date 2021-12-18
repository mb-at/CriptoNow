package com.example.criptonow

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import kotlinx.android.synthetic.main.fragment_cripto_now.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class CriptoNowFragment: Fragment() {

    private var db: CriptoNowDB? = null
    private val TAG: String = "CriptoNowFragment"

    companion object{

        //Variable donde se almacena la lista de preguntas sacada de la base de datos
        var questionsList: ArrayList<PreguntadosQuestion> = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cripto_now, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instanciamos la base de datos con su contexto para poder usarla
        db = context?.let { CriptoNowDB(it) }

        /*----------------------------------------------------------------------------------------------------------------------
        DESCOMENTAMOS ESTAS LÍNEAS DE ABAJO PARA PODER GENERAR LOS ARCHIVOS EN MEMORIA EN UN MÓVIL NUEVO O BIEN GENERARLOS DE 0.
        LUEGO LAS VOLVEMOS A COMENTAR PARA QUE NO INTERFIERA CON LA LÓGICA DEL JUEGO.

        TODOlATER:Encontrar manera de incluir esos archivos desde el principio

        ------------------------------------------------------------------------------------------------------------------------
         */

        //Generamos los archivos con las preguntas de cada categoría necesarios para el funcionamiento de la app. En este caso saldrían con los valores de 0.
        //loadQuestionsList("blockchain","preguntasBlockchain.bin")
        //Log.d(TAG,"Se ha cargado el archivo de blockchain")
        //loadQuestionsList("criptoactivos","preguntasCriptoactivos.bin")
        //Log.d(TAG,"Se ha cargado el archivo de criptoactivos")
        //loadQuestionsList("nfts","preguntasNfts.bin")
        //Log.d(TAG,"Se ha cargado el archivo de nfts")


        //Recuperamos la lista de preguntas para ver su estado
        /*val listaBlockchain = getQuestionsState("preguntasBlockchain.bin")
        val sizeBlockchain = listaBlockchain.size
        for(pregunta in listaBlockchain){

            Log.d("PreguntasBlockchain","${pregunta.pregunta}, Acertadada:${pregunta.acertada}, Contestada: ${pregunta.contestada}, Indice = ${pregunta.indice}")
        }*/

        /*val listaCriptoactivos = getQuestionsState("preguntasCriptoactivos.bin")
        val sizeCriptoactivos = listaCriptoactivos.size
        for(pregunta in listaCriptoactivos){

            Log.d("PreguntasCriptoactivos","${pregunta.pregunta}, Acertadada:${pregunta.acertada}, Contestada: ${pregunta.contestada}, Indice = ${pregunta.indice}")
        }*/


        /*val listaNfts = getQuestionsState("preguntasNfts.bin")
        val sizeNfts = listaNfts.size
        for(pregunta in listaNfts){

            Log.d("PreguntasNfts","${pregunta.pregunta} : ${pregunta.acertada}, Contestada: ${pregunta.contestada}")
        }*/



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
            val options = arrayOf("ÚLTIMAS NOTICIAS DE BLOCKCHAIN","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val blockchainOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de blockchain")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS DE BLOCKCHAIN"){

                            Toast.makeText(requireActivity().baseContext, "HOlaaaaa", Toast.LENGTH_SHORT).show()
                            val noticiasBlockchain = Intent(Intent.ACTION_VIEW, Uri.parse("https://es.cointelegraph.com/tags/blockchain"))
                            startActivity(noticiasBlockchain)
                        }
                        else{

                            val inversionBlockchain = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.businessinsider.es/criptomonedas-pueden-subir-2022-945059"))
                            startActivity(inversionBlockchain)
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            blockchainOptions.show()
        }

        opcionesCriptoActivos.setOnClickListener{

            //Instanciamos un array con las opciones que le damos al usuario
            val options = arrayOf("ÚLTIMAS NOTICIAS CRIPTO","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val criptoOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de criptoactivos")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS CRIPTO"){

                            val noticiasCripto = Intent(Intent.ACTION_VIEW, Uri.parse("https://es.cointelegraph.com/tags/cryptocurrencies"))
                            startActivity(noticiasCripto)
                        }
                        else{

                            val inversionCripto = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.infotechnology.com/finanzas-digitales/las-6-criptomonedas-poco-conocidas-para-invertir-en-2022-y-ganar-en-dolares/"))
                            startActivity(inversionCripto)
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            criptoOptions.show()
        }

        opcionesNfts.setOnClickListener{

            //Instanciamos un array con las opciones que le damos al usuario
            val options = arrayOf("ÚLTIMAS NOTICIAS DE NFTS","RECOMENDACIONES DE INVERSIÓN 2022")

            //Creamos el diálogo de opciones para el perfil
            val nftOptions = AlertDialog.Builder(context)
                    .setTitle("Opciones de Nfts")
                    .setSingleChoiceItems(options, 0) {_, i ->

                        if (options[i] == "ÚLTIMAS NOTICIAS DE NFTS"){

                            val noticiasNfts = Intent(Intent.ACTION_VIEW, Uri.parse("https://es.cointelegraph.com/tags/nft"))
                            startActivity(noticiasNfts)
                        }
                        else{

                            val inversionNfts = Intent(Intent.ACTION_VIEW, Uri.parse("https://cryptoshitcompra.com/los-10-mejores-proyectos-nft-para-invertir-en-2022"))
                            startActivity(inversionNfts)
                        }

                    }
                    .setNegativeButton("Back") {dialog, _ ->

                        dialog.dismiss()

                    }.create()

            nftOptions.show()
        }

        //Ponemos a la escucha a los botones de errores
        erroresBlockchain.setOnClickListener {

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "blockchain")

            //Enviamos el modo elegido por el usuario
            bundle.putString("modo", "errores")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }
        }

        erroresCriptoactivos.setOnClickListener {

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "criptoactivos")

            //Enviamos el modo elegido por el usuario
            bundle.putString("modo", "errores")

            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }

        }


        erroresNfts.setOnClickListener {

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "nfts")

            //Enviamos el modo elegido por el usuario
            bundle.putString("modo", "errores")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, preguntados)
                commit()
            }

        }
    }

    fun loadQuestionsList(categoria: String, nombreArchivo: String){
        /*Método únicamente utilizado para volcar los datos de la base de datos
        * a los distintos archivos binario según categorías*/

        try {

            val sqlQuery = "SELECT * FROM CRIPTOPREGUNTAS WHERE categoria = '$categoria'"
            db?.getDatabaseRow(sqlQuery)?.use {

                if (it.count > 0) {

                    do {
                        /*Extraemos los datos de la fila de la base de datos*/
                        val col = it.getColumnIndex("pregunta")
                        val preg = it.getString(col)

                        val col2 = it.getColumnIndex("rincorrecta1")
                        val rincorrecta1 = it.getString(col2)

                        val col3 = it.getColumnIndex("rincorrecta2")
                        val rincorrecta2 = it.getString(col3)

                        val col4 = it.getColumnIndex("rincorrecta3")
                        val rincorrecta3 = it.getString(col4)

                        val col5 = it.getColumnIndex("rcorrecta")
                        val rcorrecta = it.getString(col5)

                        val col6 = it.getColumnIndex("categoria")
                        val categoria = it.getString(col6)

                        val col7 = it.getColumnIndex("proyecto")
                        val proyecto = it.getString(col7)

                        val col8 = it.getColumnIndex("contestada")
                        val contestada = it.getInt(col8)

                        val col9 = it.getColumnIndex("acertada")
                        val acertada = it.getInt(col9)


                        //Rellenamos el objeto de tipo pregunta
                        val pregunta = PreguntadosQuestion(preg, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta, categoria, proyecto, contestada, acertada)

                        //la añadimos a la lista
                        questionsList.add(pregunta)


                    } while (it.moveToNext())

                }
            }

        } catch (e: Exception) {

            println("No hay más preguntas")
            e.printStackTrace()
        }

        //Guardamos la lista de preguntas en el archivo
        persistQuestionsState(nombreArchivo , questionsList)
    }


    fun persistQuestionsState(fileName: String, lista: ArrayList<PreguntadosQuestion>) {
        /**Método utilizado para generar y actualizar el archivo de objetos pregunta en memoria interna,
         * con el fin de poder consultarlo para otras tareas. Todas las preguntas van dentro a su vez de
         * un arrayList*/

        context?.openFileOutput(fileName, Context.MODE_PRIVATE)
                .use { ObjectOutputStream(it).writeObject(lista) }
    }

    fun getQuestionsState(fileName: String): ArrayList<PreguntadosQuestion> {
        /*Método utilizado para poder recuperar el estado de la lista de preguntas
        * que se encuentra en la memoria interna del dispositivo.
        * Devuelve un objeto lista con el estado de las preguntas*/

        var objeto: ArrayList<PreguntadosQuestion> = arrayListOf()
        val file = File(context?.filesDir, fileName)
        //println(file.absolutePath)

        ObjectInputStream(FileInputStream(file)).use { it ->
            //Read the family back from the file
            objeto = it.readObject() as ArrayList<PreguntadosQuestion>
        }

        return objeto
    }




}