package com.example.criptonow

import android.os.Bundle
import android.sax.EndElementListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_preguntados.*
import java.util.*
import java.util.Collections.swap


class PreguntadosFragment : Fragment() {
    /*Este fragmento va a contener la lógica del modo de juedo de preguntados para las tres
    * categorías de aprendizaje de la aplicación: blockchain, criptoactivos, nfts*/

    private val TAG = "PreguntadosFragment"
    private var db: CriptoNowDB?=null
    private var categoryPreguntados: String? = ""

    companion object{

            //Array que contiene los elementos que forman una pregunta => pos0=pregunta, pos1=ricorrecta1,
            // pos2=rincorrecta2, pos3=rincorrecta3, pos4=rcorrecta
            var question: Array<String> = arrayOf("", "", "", "", "")
            //Variable que accede a la posición del cursor cuando recupera las preguntas de la base de datos
            var posicionCursor = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_preguntados, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instanciamos la base de datos para poder usarla
        db = context?.let { CriptoNowDB(it) }

        //Recibimos la categoría deseada para el preguntados desde el otro fragment.
        categoryPreguntados = arguments?.getString("category")

        //Establecemos las preguntas de la categoría que hayamos elegido.
        setQuestion(posicionCursor, categoryPreguntados)

        pistaPreguntados.setOnClickListener{

            Toast.makeText(requireActivity(), "Funciono:)", Toast.LENGTH_SHORT).show()
        }



    }

    private fun setQuestion(cursorPosition: Int, categoryPreguntados: String?){
        /*Rellena la vista asociada con este activity con los elementos que conforman la pregunta.
        * La posición del cursor le indica la pregunta exacta que tiene que tratar en cada momento*/


        var question = getQuestion(posicionCursor, categoryPreguntados)

        preguntaPreguntados.setText(question[0])
        respuestaPreguntados1.setText(question[1])
        respuestaPreguntados2.setText(question[2])
        respuestaPreguntados3.setText(question[3])
        respuestaPreguntados4.setText(question[4])


        siguientePreguntados.setOnClickListener{

            posicionCursor += 1
            getQuestion(posicionCursor, categoryPreguntados)

            //Rellenamos los elementos de la pregunta
            preguntaPreguntados.setText(question[0])
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])
        }

        anteriorPreguntados.setOnClickListener {

            posicionCursor -= 1
            getQuestion(posicionCursor, categoryPreguntados)

            //Rellenamos los elementos de la pregunta
            preguntaPreguntados.setText(question[0])
            respuestaPreguntados1.setText(question[1])
            respuestaPreguntados2.setText(question[2])
            respuestaPreguntados3.setText(question[3])
            respuestaPreguntados4.setText(question[4])
        }
    }

    private fun getQuestion(posicion: Int, categoria: String?): Array<String> {
        /*Rellena un array formado por los elementos que forman una pregunta en una posición concreta.
        * Después de eso devuelve ese array.*/

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

                }
            }

        }catch (e: Exception){

                println("No hay más preguntas")
                //e.printStackTrace()
        }

            return question
    }

    fun getOrderPreguntados(): List<Int> {
        /*Barajamos el orden de las preguntas para darle una mayor
        * complejidad al preguntados*/

        var posiciones = listOf(1,2,3,4)
        val posicionesAleatorias = posiciones.shuffled()

        return posicionesAleatorias
    }

    /*private fun getQuestions(){
        /*Este método es para probar errores que me puedan surgir en la base de datos*/

        try {

            //val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS WHERE categoria = " + "'$categoria'"
            val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta, categoria FROM CRIPTOPREGUNTAS"
            db?.FireQuery(sqlQuery)?.use {

                if (it.count > 0) {

                    do{
                        val col = it.getColumnIndex("pregunta")
                        val preg = it.getString(col)
                        //PreguntadosFragment.pregunta.set(0,preg)

                        val col2 = it.getColumnIndex("rincorrecta1")
                        val rincorrecta1 = it.getString(col2)
                        //PreguntadosFragment.pregunta.set(1,rincorrecta1)


                        val col3 = it.getColumnIndex("rincorrecta2")
                        val rincorrecta2 = it.getString(col3)
                        //PreguntadosFragment.pregunta.set(2,rincorrecta2)


                        val col4 = it.getColumnIndex("rincorrecta3")
                        val rincorrecta3 = it.getString(col4)
                        //PreguntadosFragment.pregunta.set(3,rincorrecta3)


                        val col5 = it.getColumnIndex("rcorrecta")
                        val rcorrecta = it.getString(col5)
                        //PreguntadosFragment.pregunta.set(4,rcorrecta)

                        val col6 = it.getColumnIndex("categoria")
                        val categoria = it.getString(col6)

                        Log.d(TAG, "$preg : $rcorrecta ====> $categoria")

                    }while (it.moveToNext())

                }
            }

        }catch (e: Exception){

            e.printStackTrace()
        }


    }*/
}
