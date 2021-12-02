package com.example.criptonow

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
import java.lang.Exception


class BadgesFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var db: CriptoNowDB?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_badges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = context?.let { CriptoNowDB(it) }

        layoutManager = LinearLayoutManager(activity)
        badgesRecyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        //Asociamos nuestra vista con un objeto de nuestra clase adaptador
        badgesRecyclerView.adapter = adapter
        db?.openDatabase()
        println(db?.modificarInsignia("BitcoinerII","bebb.png"))
        readBadges()


    }

    fun readBadges() {

        try {

            val sqlQuery = "SELECT nombre, detalle, imagen FROM INSIGNIAS"
            //val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS"
            db?.FireQuery(sqlQuery)?.use {

                if (it.count > 0) {

                  do {
                      val col = it.getColumnIndex("nombre")
                      val nombre = it.getString(col)

                      val col2 = it.getColumnIndex("detalle")
                      val detalle = it.getString(col2)

                      val col3 = it.getColumnIndex("imagen")
                      val imagen = it.getString(col3)

                      Log.d("BadgesFragment", "$nombre $detalle $imagen")

                  }while (it.moveToNext())
                }
            }
        } catch (e: Exception) {

            e.printStackTrace()

        }

    }



}