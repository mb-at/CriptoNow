package com.example.criptonow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import kotlinx.android.synthetic.main.fragment_cripto_now.*

class CriptoNowFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cripto_now, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preguntados = PreguntadosFragment()

        //Listeners del fragment.
        preguntadosBlockChain.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "blockchain")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.containerView, preguntados)
                commit()
            }
        }

        preguntadosCriptoActivos.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "criptoactivos")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.containerView, preguntados)
                commit()
            }
        }

        preguntadosNfts.setOnClickListener{

            //Enviamos la categoría elegida por el usuario
            val bundle = Bundle()
            bundle.putString("category", "nfts")
            preguntados.arguments = bundle

            parentFragmentManager.beginTransaction().apply {

                replace(R.id.containerView, preguntados)
                commit()
            }
        }
    }
}