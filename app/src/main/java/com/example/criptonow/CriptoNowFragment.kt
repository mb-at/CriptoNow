package com.example.criptonow

import android.app.AlertDialog
import android.content.Context
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

            Log.d("PreguntasNfts","${pregunta.pregunta}")
        }*/
        //Log.d("PreguntasBlockchain","$listaBlockchain")
        //Log.d("PreguntasBlockchain","El tamaño de la lista de blockchain es $sizeBlockchain")

        /*val listaCriptoactivos = getQuestionsState("preguntasCriptoactivos.bin")
        val sizeCriptoactivos = listaCriptoactivos.size
        for(pregunta in listaCriptoactivos){

            Log.d("PreguntasNfts","${pregunta.pregunta}")
        }
        //Log.d("PreguntasCriptoactivos","$listaCriptoactivos")
        Log.d("PreguntasCriptoactivos","El tamaño de la lista de criptoactivos es $sizeCriptoactivos")*/

        /*val listaNfts = getQuestionsState("preguntasNfts.bin")
        val sizeNfts = listaNfts.size
        for(pregunta in listaNfts){

            Log.d("PreguntasNfts","${pregunta.pregunta}")
        }
        Log.d("PreguntasNfts","El tamaño de la lista de nfts es $sizeNfts")*/


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