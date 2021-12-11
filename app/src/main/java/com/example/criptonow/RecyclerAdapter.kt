package com.example.criptonow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var db: CriptoNowDB?=null

    //Creamos los arrays que contienen los elementos de cada insignia en nuestras cardView
    private var titles: Array<String> = arrayOf("Bitcoiner I","Bitcoiner II","Ethereum I","Ethereum II","Ripple Beginner","Ada Beginner")
    private var details: Array<String> = arrayOf("Has conseguido completar cinco preguntas de manera correcta acerca de Bitcoin.",
            "Has conseguido completar diez preguntas de manera correcta acerca de Bitcoin.",
            "Has conseguido completar cinco preguntas de manera correcta acerca de Ethereum.",
            "Has conseguido completar diez preguntas de manera correcta acerca de Ethereum.","Conoces lo básico del proyecto de Ripple.",
            "Conoces lo básico del proyecto de Cardano.")

    private var images = intArrayOf(R.drawable.shiba,R.drawable.shiba,R.drawable.shiba,R.drawable.shiba,R.drawable.shiba,R.drawable.shiba)

    private var badges = intArrayOf(R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_badges_layout, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        holder.cardTitle.text = titles[position]
        holder.cardDescrition.text = details[position]
        holder.cardImage.setImageResource(images[position])
        holder.cardBadge.setImageResource(badges[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var cardImage: ImageView
        var cardTitle: TextView
        var cardDescrition: TextView
        var cardBadge: ImageView

        init{

            cardImage = itemView.findViewById(R.id.cardImage)
            cardTitle = itemView.findViewById(R.id.cardTitle)
            cardDescrition = itemView.findViewById(R.id.cardDescription)
            cardBadge = itemView.findViewById(R.id.gotIt)

            itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context,"${details[position]}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}