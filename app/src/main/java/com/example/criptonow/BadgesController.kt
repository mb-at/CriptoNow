package com.example.criptonow

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

public class BadgesController: AppCompatActivity() {
    /*Genera la lista de insignias de la base de datos, para que se pueda
    * ser pasada al RecyclerAdapter, VER VÍDEO.*/

    companion object{

        private var badgesList:ArrayList<Badge> = ArrayList();
    }

    private var TAG: String = "BadgesController"
    private var db: CriptoNowDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = CriptoNowDB(this)

    }

    //Generar lista insignias
    fun generateBadgesList(): ArrayList<Badge> {

        try {

            val sqlQuery = "SELECT titulo, detalle, imagen FROM INSIGNIAS"
            //val sqlQuery = "SELECT pregunta, rincorrecta1, rincorrecta2, rincorrecta3, rcorrecta FROM CRIPTOPREGUNTAS"
            db?.FireQuery(sqlQuery)?.use {

                if (it.count > 0) {

                  do {

                      val col = it.getColumnIndex("titulo")
                      val title = it.getString(col)

                      val col2 = it.getColumnIndex("detalle")
                      val detail = it.getString(col2)

                      val col3 = it.getColumnIndex("imagen")
                      val image = it.getString(col3)

                      var badge = Badge(title, detail, image)
                      badgesList.add(badge)

                  }while (it.moveToNext())
                }
            }

        }catch (e: Exception){

            println("No hay más preguntas")
            //e.printStackTrace()
        }

        return badgesList
    }

}