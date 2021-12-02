package com.example.criptonow

import android.annotation.SuppressLint
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
import java.util.*
import java.util.Collections.swap
import java.util.function.Predicate


class PreguntadosFragment : Fragment() {
    /*Este fragmento va a contener la lógica del modo de juedo de preguntados para las tres
    * categorías de aprendizaje de la aplicación: blockchain, criptoactivos, nfts*/

    private val TAG = "PreguntadosFragment"
    private var db: CriptoNowDB?=null
    private var categoryPreguntados: String? = ""

    companion object{

            //Array que contiene los elementos que forman una pregunta => pos0=pregunta, pos1=ricorrecta1,
            // pos2=rincorrecta2, pos3=rincorrecta3, pos4=rcorrecta, pos5=indice de la respuesta correcta
            var question: Array<String> = arrayOf("", "", "", "", "","")
            //Variable que accede a la posición del cursor cuando recupera las preguntas de la base de datos
            var posicionCursor = 0
            //Variable que accede al número de la pregunta
            var contadorPreguntas = 1
            //ArrayList que guarda las posiciones de las respuestas marcadas por el usuario
            var selected: Array<Int> = arrayOf(0,0,0,0,0)
            //Contador que almacena las respuestas correctas de cada partida
            var nrespuestasCorrectas: Int = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_preguntados, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Como ha empezado la partida el resultado está invisible
        resultadoPartida.visibility = View.INVISIBLE

        //Instanciamos la base de datos para poder usarla
        db = context?.let { CriptoNowDB(it) }

        //Recibimos la categoría deseada para el preguntados desde el otro fragment.
        categoryPreguntados = arguments?.getString("category")

        //Establecemos los elementos de las preguntas de la categoría que hayamos elegido.
        questionLogic(posicionCursor, categoryPreguntados)

    }

    private fun questionLogic(cursorPosition: Int, categoryPreguntados: String?){
        /*Rellena la vista asociada con este activity con los elementos que conforman la pregunta y la lógica de los botones del fragmento.
        * La posición del cursor le indica la pregunta exacta que tiene que tratar en cada momento*/

        //Sacamos los elementos de la pregunta que corresponda a la posición del parámetro
        var question = getQuestion(posicionCursor, categoryPreguntados)

        //Los establecemos en los textview
        preguntaPreguntados.setText(contadorPreguntas.toString() + "-" + question[0])
        respuestaPreguntados1.setText(question[1])
        respuestaPreguntados2.setText(question[2])
        respuestaPreguntados3.setText(question[3])
        respuestaPreguntados4.setText(question[4])


        anteriorPreguntados.visibility = View.INVISIBLE

        //Ponemos las respuesta a la escucha para ver si son clickadas o no.
        respuestaPreguntados1.setOnClickListener {

            selected[posicionCursor] = 1
            //Comprobamos si la respuesta seleccionada por el usuario es la correcta
            if(selected[posicionCursor].equals(question[5].toInt())){

                nrespuestasCorrectas += 1
            }
            Log.d("Posición seleccionada", "${selected[posicionCursor]}")


        }
        respuestaPreguntados2.setOnClickListener {

            selected[posicionCursor] = 2
            //Comprobamos si la respuesta seleccionada por el usuario es la correcta
            if(selected[posicionCursor].equals(question[5].toInt())){

                nrespuestasCorrectas += 1
            }
            Log.d("Posición seleccionada", "${selected[posicionCursor]}")

        }
        respuestaPreguntados3.setOnClickListener {

            selected[posicionCursor] = 3
            //Comprobamos si la respuesta seleccionada por el usuario es la correcta
            if(selected[posicionCursor].equals(question[5].toInt())){

                nrespuestasCorrectas += 1
            }
            Log.d("Posición seleccionada", "${selected[posicionCursor]}")

        }
        respuestaPreguntados4.setOnClickListener {

            selected[posicionCursor] = 4
            //Comprobamos si la respuesta seleccionada por el usuario es la correcta
            if(selected[posicionCursor].equals(question[5].toInt())){

                nrespuestasCorrectas += 1
            }
            Log.d("Posición seleccionada", "${selected[posicionCursor]}")
        }

        Log.d("Posición correcta", "${question[5].toInt()}")




        //PASAMOS A LA SIGUIENTE PREGUNTA
        siguientePreguntados.setOnClickListener{

            anteriorPreguntados.visibility = View.VISIBLE

            contadorPreguntas += 1
            posicionCursor += 1

            //Terminamos la partidas cuando se hayan completaddo las 5 preguntas
            if(posicionCursor == 5){

                //Invisivilizamos los botones de acción porque se ha acabado esa partida
                accionesPreguntados.visibility = View.INVISIBLE

                //Hacemos visible el resultado que ha obtenido el usuario en la partida
                resultadoPartida.visibility = View.VISIBLE

                //Ponemos al usuario las respuesta correcta que ha obtenido
                resultado.setText("Has acertado $nrespuestasCorrectas/5 preguntas")

                //Restablecemos su valor para la siguiente partida
                nrespuestasCorrectas = 0

                for (position in selected){

                    Log.d(TAG, "$position")
                }

            }

            getQuestion(posicionCursor, categoryPreguntados)

            preguntaPreguntados.setText(contadorPreguntas.toString() + "-" + question[0])
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])

            respuestaPreguntados1.setOnClickListener {

                selected[posicionCursor] = 1
                if(selected[posicionCursor].equals(question[5].toInt())){

                    nrespuestasCorrectas += 1
                }

            }
            respuestaPreguntados2.setOnClickListener {

                selected[posicionCursor] = 2
                if(selected[posicionCursor].equals(question[5].toInt())){

                    nrespuestasCorrectas += 1
                }

            }
            respuestaPreguntados3.setOnClickListener {

                selected[posicionCursor] = 3
                if(selected[posicionCursor].equals(question[5].toInt())){

                    nrespuestasCorrectas += 1
                }


            }
            respuestaPreguntados4.setOnClickListener {

                selected[posicionCursor] = 4
                if(selected[posicionCursor].equals(question[5].toInt())){

                    nrespuestasCorrectas += 1
                }

            }

            //Comprobamos si la respuesta seleccionada por el usuario es la correcta
            /*if(selected[posicionCursor] == question[5].toInt()){

                nrespuestaCorrectas += 1
            }*/

        }

        //RETROCEDEMOS A LA PREGUNTA ANTERIOR
        anteriorPreguntados.setOnClickListener {

            contadorPreguntas -= 1
            posicionCursor -= 1

            //Cuando esté en la primera pregunta, desactivamos el botón de atrás
            if(posicionCursor == 0){

                anteriorPreguntados.visibility = View.INVISIBLE
            }

            getQuestion(posicionCursor, categoryPreguntados)

            preguntaPreguntados.setText(contadorPreguntas.toString() + "-" + question[0])
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])

        }

        pistaPreguntados.setOnClickListener {

            cluesDialog()
        }

        salirPartidaPreguntados.setOnClickListener {

            //Reseteamos las posiciones de cursor y contador
            posicionCursor = 0
            contadorPreguntas = 1

            //Volvemos al fragment de criptoNow
            var criptoNowFragment = CriptoNowFragment()

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.appfragments, criptoNowFragment)
                commit()
            }

        }

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

        })

        builder?.setNegativeButton("Back", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        //Creamos nuestro diálogo con nuestras características
        val alertDialog = builder?.create()
        alertDialog?.show()

    }

    private fun getQuestion(posicion: Int, categoria: String?): Array<String> {
        /*Rellena un array formado por los elementos que forman una pregunta en una posición concreta.
        * Después de eso devuelve ese array con el contenido de la pregunta.*/

        try {

            val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS WHERE categoria = '$categoria'"
            //val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS"
            db?.FireQuery(sqlQuery)?.use {

                //Mueve el cursor a la posición que indique el parámetro.
                it.moveToPosition(posicion)

                //Generamos un orden aleatorio para la colocación de las respuestas
                var randomOrder = getOrderPreguntados()

                if (it.count > 0) {

                    /*Asigna los valores al array que conforma la pregunta de manera aletoria.
                     De tal forma que la pregunta que la pregunta correcta varíe de posición*/
                    val col = it.getColumnIndex("pregunta")
                    val preg = it.getString(col)
                    question.set(0, preg)

                    val col2 = it.getColumnIndex("rincorrecta1")
                    val rincorrecta1 = it.getString(col2)
                    question.set(randomOrder[0], rincorrecta1)


                    val col3 = it.getColumnIndex("rincorrecta2")
                    val rincorrecta2 = it.getString(col3)
                    question.set(randomOrder[1], rincorrecta2)


                    val col4 = it.getColumnIndex("rincorrecta3")
                    val rincorrecta3 = it.getString(col4)
                    question.set(randomOrder[2], rincorrecta3)


                    val col5 = it.getColumnIndex("rcorrecta")
                    val rcorrecta = it.getString(col5)
                    question.set(randomOrder[3], rcorrecta)

                    //Añadimos el índice de la repuesta correcta al array para saber dónde está
                    question.set(5, randomOrder[3].toString())

                }
            }



        }catch (e: Exception){

                println("No hay más preguntas")
                //e.printStackTrace()
        }

            return question
    }

    fun getOrderPreguntados(): List<Int> {
        /*Barajamos el orden de las respuestas correcta en incorrectas para darle una mayor
        * complejidad al preguntados*/

        var posiciones = listOf(1,2,3,4)
        val posicionesAleatorias = posiciones.shuffled()

        return posicionesAleatorias
    }

}

/*respuestaPreguntados1.setOnClickListener {

            PreguntadosFragment.selected[PreguntadosFragment.posicionCursor] = 1
            respuestaPreguntados1.setBackgroundColor(getResources().getColor(R.color.teal_700))
            respuestaPreguntados2.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados3.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados4.setBackgroundColor(getResources().getColor(R.color.darkmustard))
        }
        respuestaPreguntados2.setOnClickListener {

            PreguntadosFragment.selected[PreguntadosFragment.posicionCursor] = 2
            respuestaPreguntados1.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados2.setBackgroundColor(getResources().getColor(R.color.teal_700))
            respuestaPreguntados3.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados4.setBackgroundColor(getResources().getColor(R.color.darkmustard))
        }
        respuestaPreguntados3.setOnClickListener {

            PreguntadosFragment.selected[PreguntadosFragment.posicionCursor] = 3
            respuestaPreguntados1.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados2.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados3.setBackgroundColor(getResources().getColor(R.color.teal_700))
            respuestaPreguntados4.setBackgroundColor(getResources().getColor(R.color.darkmustard))
        }
        respuestaPreguntados4.setOnClickListener {

            PreguntadosFragment.selected[PreguntadosFragment.posicionCursor] = 4
            respuestaPreguntados1.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados2.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados3.setBackgroundColor(getResources().getColor(R.color.darkmustard))
            respuestaPreguntados4.setBackgroundColor(getResources().getColor(R.color.teal_700))
        }*/