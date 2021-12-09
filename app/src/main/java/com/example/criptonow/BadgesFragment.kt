package com.example.criptonow

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criptonow.R
import kotlinx.android.synthetic.main.fragment_badges.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception


class BadgesFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var db: CriptoNowDB? = null
    private val TAG: String = "BadgesFragment"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_badges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        badgesRecyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        //Asociamos nuestra vista con un objeto de nuestra clase adaptador
        badgesRecyclerView.adapter = adapter

        db = context?.let { CriptoNowDB(it) }

        //Leemos el estado del archivo de preguntas para ver si podemos desbloquear alguna insignia
       // println(getStateQuestions())

    }

    fun getStateQuestions(): ArrayList<PreguntadosQuestion> {
        /*Método utilizado para poder recuperar el estado de la lista de preguntas
        * que se encuentra en la memoria interna del dispositivo*/

        var objeto: ArrayList<PreguntadosQuestion> = arrayListOf()
        val file = File(context?.filesDir, "preguntasCripto.bin")
        println(file.absolutePath)

        ObjectInputStream(FileInputStream(file)).use { it ->
            //Read the family back from the file
            objeto = it.readObject() as ArrayList<PreguntadosQuestion>
        }

        return objeto
    }


}


