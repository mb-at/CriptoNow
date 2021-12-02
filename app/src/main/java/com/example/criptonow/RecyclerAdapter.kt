package com.example.criptonow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var db: CriptoNowDB?=null
    private var badges = BadgesController()

    //Creamos los arrays que contienen los elementos de cada insignia en nuestras cardView
    private var badgesList: ArrayList<Badge> = badges.generateBadgesList();
    //private var titles: Array<String> = arrayOf("Bitcoiner","title2","title3","title4","title5")
    //private var details: Array<String> = arrayOf("Has conseguido completar diez preguntas de manera correcta acerca de Bitcoin.","detail2","detail3","detail4","detail5")
    private var images = intArrayOf(R.drawable.shiba)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_badges_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        holder.cardTitle.text = badgesList.get(position).title
        holder.cardDescrition.text = badgesList.get(position).detail
        holder.cardImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return badgesList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var cardImage: ImageView
        var cardTitle: TextView
        var cardDescrition: TextView

        init{

            cardImage = itemView.findViewById(R.id.cardImage)
            cardTitle = itemView.findViewById(R.id.cardTitle)
            cardDescrition = itemView.findViewById(R.id.cardDescription)

            /*itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context,"you clicked on ${badgesList[position]}", Toast.LENGTH_SHORT).show()
            }*/
        }

    }

}