package com.example.criptonow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_preguntados.*
import java.lang.Exception

class PreguntadosFragment : Fragment() {
    /*Este fragmento va a contener la lógica del modo de juedo de preguntados para las tres
    * categorías de aprendizaje de la aplicación: blockchain, criptoactivos, nfts*/

    private val TAG = "PreguntadosFragment"
    private var db:CriptoNowDB?=null

    companion object{
        private var pregunta = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preguntados, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = CriptoNowDB(this)


        siguientePreguntados.setOnClickListener{

               getQuestion(18)

        }



    }

    private fun getQuestion(posicion: Int){

       try {

           //val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS WHERE categoria = " + "'$categoria'"
           val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS"
           db?.FireQuery(sqlQuery)?.use {

               if(it.count > 0) {

                   do {

                       Log.d(TAG,"Count: ${it.count}")

                       val col = it.getColumnIndex("pregunta")
                       PreguntadosFragment.pregunta = it.getString(col)




                   }while (it.moveToNext())

               }
            }
           }catch (e: Exception){

           e.printStackTrace()
       }
   }


    //In Fragments use:
    //Toast.makeText(requireActivity(), "your message", Toast.LENGTH_LONG).show()
}

/*val col = it.getColumnIndex("pregunta")
val pregunta = it.getString(col)
preguntaPreguntados.setText(pregunta)

val col2 = it.getColumnIndex("rincorrecta1")
val rincorrecta1 = it.getString(col2)
respuestaPreguntados1.setText(rincorrecta1)

val col3 = it.getColumnIndex("rincorrecta2")
val rincorrecta2 = it.getString(col3)
respuestaPreguntados2.setText(rincorrecta2)

val col4 = it.getColumnIndex("rincorrecta3")
val rincorrecta3 = it.getString(col4)
respuestaPreguntados3.setText(rincorrecta3)

val col5 = it.getColumnIndex("rcorrecta")
val rcorrecta = it.getString(col5)
respuestaPreguntados4.setText(rcorrecta)*/