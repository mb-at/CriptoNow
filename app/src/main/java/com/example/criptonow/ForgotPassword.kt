package com.example.criptonow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //Añadimos escuchador al botón para desencadenar la acción de enviar el email.
        submitButton.setOnClickListener{

            //Recogemos el email del usuario
            val email: String  = etForgotEmail.text.toString().trim { it <= ' ' }

            if(email.isEmpty()){

                Toast.makeText(this, "Por favor, introduce el email", Toast.LENGTH_SHORT).show()

            }else{

                //Envíamos el email para poder resetear la contraseña de nuestra cuenta en Firebase.
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{
                        if(it.isSuccessful){

                            Toast.makeText(this, "El email se envió correctamente", Toast.LENGTH_SHORT).show()

                            //Volvemos a la actividad desde la que se lanzó esta
                            finish()
                        }else{

                            Toast.makeText(this,
                            it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }

        //Validar que es un correo básico y que no existe ya en la base de datos. Y aparte también validar que es un correo válido.
    }
}