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
        var preguntados = PreguntadosFragment()

        criptoassetsIV.setOnClickListener{

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.containerView, preguntados)
                commit()
            }
        }
    }





    //In Fragments use:
    //Toast.makeText(requireActivity(), "your message", Toast.LENGTH_LONG).show()
}