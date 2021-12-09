package com.example.criptonow

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.sax.EndElementListener
import android.system.Os.remove
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.criptonow.R.color.design_default_color_on_primary
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_preguntados.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*
import java.util.Collections.swap
import java.util.function.Predicate
import kotlin.collections.ArrayList
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


class PreguntadosFragment : Fragment() {
    /*Este fragmento va a contener la lógica del modo de juedo de preguntados para las tres
    * categorías de aprendizaje de la aplicación: blockchain, criptoactivos, nfts*/

    private val TAG = "PreguntadosFragment"
    private var db: CriptoNowDB? = null


    companion object {

        //Array que contiene los elementos que forman una pregunta => pos0=pregunta, pos1=ricorrecta1,
        // pos2=rincorrecta2, pos3=rincorrecta3, pos4=rcorrecta, pos5=indice de la respuesta correcta pos6= respuestaseleccionada
        var question: Array<String> = arrayOf("", "", "", "", "", "", "")

        //Variable que accede a la posición del cursor cuando recupera las preguntas de la base de datos
        var listPosition = 0

        //Variable que accede al número de la pregunta
        var questionCounter = 1

        //ArrayList que guarda las posiciones de las respuestas marcadas por el usuario
        var selected: Array<String> = arrayOf("","","","","")

        //ArrayList que guarda las posiciones correctas de las preguntas
        var correctas: Array<String> = arrayOf("1","1","1","1","1")

        //Contador que almacena las respuestas correctas de cada partida
        var nrespuestasCorrectas: Int = 0

        //Variable donde se almacena la lista de preguntas sacada de la base de datos
        var questionsList: ArrayList<PreguntadosQuestion> = ArrayList()

        //Variable donde se almacena el nombre de la categoría que se ha escogido
        var categoryPreguntados: String? = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_preguntados, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ESTO ES UNA PRUEBA QUE CARGA EL ESTADO DEL ARCHIVO DE CRIPTOACTIVOS DESPUÉS DE UNA MODIFICACIÓN: fUNCIONA :)
        val estadoCriptoActivos = getQuestionsState("preguntasCriptoactivos.bin")
        println("$estadoCriptoActivos")

        //Como ha empezado la partida el resultado está invisible
        resultadoPartida.visibility = View.INVISIBLE

        //Empezamos desde la pregunta 1 por tanto no permitimos ir para atrás según carga el activity
        anteriorPreguntados.visibility = View.INVISIBLE

        //Recibimos la categoría deseada para el preguntados desde el otro fragment.
        categoryPreguntados = arguments?.getString("category")

        //Instanciamos la base de datos con su contexto para poder usarla
        db = context?.let { CriptoNowDB(it) }

        //Evaluamos que categoría se ha escodigo para escoger el archivo y generar la lista
        if (categoryPreguntados.equals("criptoactivos")){

           questionsList = getQuestionsState("preguntasCriptoactivos.bin")
        }
        if (categoryPreguntados.equals("blockchain")){

            questionsList = getQuestionsState("preguntasBlockchain.bin")
        }
        if (categoryPreguntados.equals("nfts")){

            questionsList = getQuestionsState("preguntasNfts.bin")
        }

        //Recuperamos los elementos necesarios para establecer la lógica del preguntados
        setQuestion(listPosition)

        var pregunta = questionCounter.toString() + "-" +question[0]

        //Establecemos las preguntas en los textView
        preguntaPreguntados.setText(pregunta)
        respuestaPreguntados1.setText(question[1])
        respuestaPreguntados2.setText(question[2])
        respuestaPreguntados3.setText(question[3])
        respuestaPreguntados4.setText(question[4])

        //Ponemos las respuesta a la escucha para ver si son clickadas o no.
        respuestaPreguntados1.setOnClickListener {

            selected[listPosition] = "1"
            correctas[listPosition] = question[5]

        }
        respuestaPreguntados2.setOnClickListener {

            selected[listPosition] = "2"
            correctas[listPosition] = question[5]

        }
        respuestaPreguntados3.setOnClickListener {

            selected[listPosition] = "3"
            correctas[listPosition] = question[5]

        }
        respuestaPreguntados4.setOnClickListener {

            selected[listPosition] = "4"
            correctas[listPosition] = question[5]

        }

        //Cambiamos el estado de esa pregunta a contestada
        //questionsList[listPosition].contestada = 1


        //LÓGICA DE BOTÓN DE SIGUIENTE
        siguientePreguntados.setOnClickListener {

            anteriorPreguntados.visibility = View.VISIBLE

            //Cambiamos el estado de esa pregunta a contestada
            questionsList[listPosition].contestada = 1

            questionCounter += 1
            listPosition += 1
            setQuestion(listPosition)

            //Terminamos la partidas cuando se hayan completaddo las 5 preguntas
            if(listPosition == 5){

                //Invisivilizamos todos los elementos del preguntados
                preguntaPreguntados.visibility = View.INVISIBLE
                respuestaPreguntados1.visibility = View.INVISIBLE
                respuestaPreguntados2.visibility = View.INVISIBLE
                respuestaPreguntados3.visibility = View.INVISIBLE
                respuestaPreguntados4.visibility = View.INVISIBLE
                accionesPreguntados.visibility = View.INVISIBLE

                //Hacemos visible el resultado que ha obtenido el usuario en la partida
                resultadoPartida.visibility = View.VISIBLE

                for(seleccionada in selected){

                    Log.d("Seleccionadas", "$seleccionada")
                }

                for(correcta in correctas){

                    Log.d("Correctas", "$correcta")
                }

                var cont=0

                //Sumamos la respuestas correctas
                for(correcta in correctas){

                    if(selected[cont] == correcta){
                        nrespuestasCorrectas += 1
                    }
                    cont+=1
                }

                //Ponemos al usuario las respuesta correcta que ha obtenido
                var score = "Has acertado $nrespuestasCorrectas/5 preguntas"
                resultado.setText(score)

                //Reseteamos a 0 el seleccionadas
                selected = arrayOf("","","","","")


                Log.d("Categoría seleccionada","$categoryPreguntados")

                //VOLVEMOS A REESCRIBIR EL ESTADO DE LAS PREGUNTAS CON LAS MODIFICACIONES QUE SE HAN HECHO DURANTE LA PARTIDA
                if (categoryPreguntados.equals("criptoactivos")){

                    persistQuestionsState("preguntasCriptoactivos.bin", questionsList)
                }
                if (categoryPreguntados.equals("blockchain")){

                    persistQuestionsState("preguntasBlockchain.bin", questionsList)
                }
                if (categoryPreguntados.equals("nfts")){

                    persistQuestionsState("preguntasNfts.bin", questionsList)
                }

            }

            var pregunta = questionCounter.toString() + "-" +question[0]

            preguntaPreguntados.setText(pregunta)
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])

            respuestaPreguntados1.setOnClickListener {

                selected[listPosition] = "1"
                correctas[listPosition] = question[5]


            }
            respuestaPreguntados2.setOnClickListener {

                selected[listPosition] = "2"
                correctas[listPosition] = question[5]

            }
            respuestaPreguntados3.setOnClickListener {

                selected[listPosition] = "3"
                correctas[listPosition] = question[5]

            }
            respuestaPreguntados4.setOnClickListener {

                selected[listPosition] = "4"
                correctas[listPosition] = question[5]
            }


        }

        //LÓGICA DE BOTÓN DE IR PARA ATRÁS
        anteriorPreguntados.setOnClickListener {

            questionCounter -= 1
            listPosition -= 1

            //Cuando esté en la primera pregunta, desactivamos el botón de atrás
            if(listPosition == 0){

                anteriorPreguntados.visibility = View.INVISIBLE
            }

            //Recuperamos los elementos necesarios para establecer la lógica del preguntados
            setQuestion(listPosition)

            //Establecemos las preguntas en los textView
            preguntaPreguntados.setText(questionCounter.toString() + "-" +question[0])
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])


        }

        //LÓGICA DE BOTÓN DE PEDIR PISTA
        pistaPreguntados.setOnClickListener {

            //Ejecutamos el diálogo y funcionalidad de la pista
            cluesDialog()
        }

        //LÓGICA DE BOTÓN DE SALIR DE LA PARTIDA
        salirPartidaPreguntados.setOnClickListener {

            //Restablecemos los valores iniciales
            listPosition = 0
            questionCounter = 1
            nrespuestasCorrectas = 0

            //Volvemos al fragment de criptoNow
            var criptoNowFragment = CriptoNowFragment()

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, criptoNowFragment)
                commit()
            }

        }

    }

    fun setQuestion(listPosition: Int): Array<String> {
        /*Método que recupera los elementos necesarios para la lógica del preguntados*/

        //Generamos un orden aleatorio para la colocación de las respuestas
        var randomOrder = getOrderPreguntados()

        //Rellenamos el array necesario para establecer cada pregunta
        question.set(0, questionsList[listPosition].pregunta)
        question.set(randomOrder[0], questionsList[listPosition].rincorrecta1)
        question.set(randomOrder[1], questionsList[listPosition].rincorrecta2)
        question.set(randomOrder[2], questionsList[listPosition].rincorrecta3)
        question.set(randomOrder[3], questionsList[listPosition].rcorrecta)
        question.set(5, randomOrder[3].toString())

        return question
    }

    fun getOrderPreguntados(): List<Int> {
        /*Barajamos el orden de las respuestas correcta en incorrectas para darle una mayor
        * complejidad al preguntados*/

        var posiciones = listOf(1, 2, 3, 4)
        val posicionesAleatorias = posiciones.shuffled()

        return posicionesAleatorias
    }

    private fun cluesDialog(){
        /*Pregunta al usuario si quiere utilizar una pista, y la gasta en
        caso de que el usuario seleccione que si*/

        val builder = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("¿Quieres gastar una pista en esta pregunta?")
        builder?.setPositiveButton("Sí", DialogInterface.OnClickListener { dialog, which ->

            //Lista auxiliar con las posiciones
            var listaPosiciones = mutableListOf(1, 2, 3, 4)

            //Lista que vamos a rellenar con las nuevas posiciones sin la posición de la correcta
            var listaPosicionesIncorrectas: MutableList<Int> = mutableListOf()
            var posicionRespuestaCorrecta = question[5].toInt()

            //Quitamos el índice de la posición correcta y rellenamos la nueva lista
            for(number in listaPosiciones){
                if (number != posicionRespuestaCorrecta){
                    listaPosicionesIncorrectas.add(number)
                }
            }

            //Barajamos la lista para coger una respuesta incorrecta aleatoria
            var lista = listaPosicionesIncorrectas.shuffled()

            //Escogemos una respuesta incorrecta de manera aleatoria
            var incorrectaAleatoria = lista[0]

            //Sustituimos el contenido del EditText por la el texto de la pista
            if(incorrectaAleatoria == 1){
                respuestaPreguntados1.setText("Esta no es :)")
            }
            if(incorrectaAleatoria == 2){
                respuestaPreguntados2.setText("Esta no es :)")
            }
            if(incorrectaAleatoria == 3){
                respuestaPreguntados3.setText("Esta no es :)")
            }
            if(incorrectaAleatoria == 4){
                respuestaPreguntados4.setText("Esta no es :)")
            }

            //Obtenemos el archivo de pistas
            val cluesPref = requireActivity().getSharedPreferences(getString(R.string.cluesNumber),Context.MODE_PRIVATE)

            //Obtenemos el número de pistas actual para restarle 1, ya que se ha gastado la pista
            var cluesNumber = cluesPref.getInt("numPistas", 0)

            //Se lo restamos
            cluesNumber -= 1

            val editor = cluesPref.edit()
            editor?.apply {

                //Guardamos en el archivo de pistas el valor con el número modificado por la pista gastada
                putInt("numPistas", cluesNumber)

            }?.apply()

            Log.d(TAG, "Se ha gastado una pista")

        })

        builder?.setNegativeButton("Back") { dialog, which ->
            dialog.dismiss()
        }

        //Creamos nuestro diálogo con nuestras características
        val alertDialog = builder?.create()
        alertDialog?.show()

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



