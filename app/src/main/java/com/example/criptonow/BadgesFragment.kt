package com.example.criptonow

import android.content.ContentValues
import android.content.Context
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

    companion object{

        //Contadores que determinan si desboqueas la primera insignia de blockchain
        var blockchainBeginner = 0

        var bitcoinBegginer = 0

        var nftBegginer = 0

        var ethereumMaster = 0

        var nftGod = 0

        var nInsignias = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_badges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //ESTE EL CÓDIGOQ QUE HAY QUE DESCOMENTAR SI NO SE PUEDE Y HAY QUE VOLVER AL ADAPTADOR
        /*layoutManager = LinearLayoutManager(activity)
        badgesRecyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        //Asociamos nuestra vista con un objeto de nuestra clase adaptador
        badgesRecyclerView.adapter = adapter*/

        //EVALUMOS SI SE CUMPLEN LOS REQUISITOS PARA QUE SE DESBLOQUEEN LAS INSIGNIAS

        //Recuperamos el estado del archivo de blockchain
        val estadoBlockchain = getStateQuestions("preguntasBlockchain.bin")

        //Recuperamos el estado del archivo de criptoactivos
        val estadoCripto = getStateQuestions("preguntasCriptoactivos.bin")

        //Recuperamos el estado del archivo de nfts
        val estadoNfts = getStateQuestions("preguntasNfts.bin")

        //Bucles que gestionan los contadores de las insignias
        for(pregunta in estadoBlockchain){

            if(pregunta.acertada == 1){

                //Sumamos al contador de BlockchainBeginner
                blockchainBeginner +=1
            }
        }

        for(preg in estadoCripto){

            if(preg.acertada == 1 && preg.proyecto == "bitcoin"){

                //Sumamos al contador de Bitcoin Beggine
                bitcoinBegginer += 1
            }

            if(preg.acertada == 1 && preg.proyecto == "ethereum"){

                //Sumamos al contador de Ethereum Master
                ethereumMaster += 1
            }
        }

        for(pregun in estadoNfts){

            if(pregun.acertada == 1){

                //Sumamos los contadores de las insignias de los nfts
                nftBegginer += 1
                nftGod += 1
            }
        }

        //EVALUAMOS LAS CONDICIONES PARA VER SI SE PUEDE DESBLOQUEAR LA INSIGNIA
        if(bitcoinBegginer >= 5){

            //Establecemos la insignia
            badge1.setImageResource(R.drawable.achievement)

            //Sumamos una insignia
            nInsignias += 1

        }else{

            //Establecemos una imagen que indique que no la has conseguido
            badge1.setImageResource(R.drawable.question)
        }

        if(blockchainBeginner >= 10){

            //Establecemos la insignia
            badge2.setImageResource(R.drawable.achievement)

            //Sumamos una insignia
            nInsignias += 1

        }else{

            //Establecemos una imagen que indique que no la has conseguido
            badge2.setImageResource(R.drawable.question)
        }

        if(nftBegginer >= 10){

            //Establecemos la insignia
            badge3.setImageResource(R.drawable.achievement)

            //Sumamos una insignia
            nInsignias += 1

        }else{

            //Establecemos una imagen que indique que no la has conseguido
            badge3.setImageResource(R.drawable.question)
        }

        if(ethereumMaster >= 10){

            //Establecemos la insignia
            badge4.setImageResource(R.drawable.achievement)

            //Sumamos una insignia
            nInsignias += 1

        }else{

            //Establecemos una imagen que indique que no la has conseguido
            badge4.setImageResource(R.drawable.question)
        }

        if(nftGod >= 25){

            //Establecemos la insignia
            badge5.setImageResource(R.drawable.achievement)

            //Sumamos una insignia
            nInsignias += 1

        }else{

            //Establecemos una imagen que indique que no la has conseguido
            badge5.setImageResource(R.drawable.question)
        }

        //Obtenemos el preferenceManager del archivo de datos del perfil
        val prefs = activity?.getSharedPreferences(getString(R.string.profile_data), Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.apply {

            //Guardamos en el perfil el número de insignias que se ha desbloqueado hasta el momento
            putInt("badges", nInsignias)

            //Reiniciamos el número de insignias para que no se sume.
            nInsignias = 0
        }?.apply()

    }

    fun getStateQuestions(fileName: String): ArrayList<PreguntadosQuestion> {
        /*Método utilizado para poder recuperar el estado de la lista de preguntas
        * que se encuentra en la memoria interna del dispositivo*/

        var objeto: ArrayList<PreguntadosQuestion> = arrayListOf()
        val file = File(context?.filesDir, fileName)
        println(file.absolutePath)

        ObjectInputStream(FileInputStream(file)).use { it ->
            //Read the family back from the file
            objeto = it.readObject() as ArrayList<PreguntadosQuestion>
        }

        return objeto
    }


}


