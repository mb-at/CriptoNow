package com.example.criptonow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100
    private var db: CriptoNowDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //setup
        setup()
        session()

        //Añadimos el escuchador para iniciar el proceso de recuperación de contraseña
        forgotPassword.setOnClickListener{
            startActivity(Intent(this, ForgotPassword::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        db = CriptoNowDB(this)
        authLayout.visibility = View.VISIBLE
    }

    private fun session(){

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email: String? =  prefs.getString("email",null)
        val provider: String? =  prefs.getString("provider",null)

        if(email != null && provider != null){

            //En caso de no existir sesión ocultamos el layout de autenticación.
            authLayout.visibility = View.INVISIBLE

            //Navegamos directamente a la pantalla de inicio.
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    private fun setup(){

        title = "Autenticación"

        //Establecemos escuchador del botón de registro.
        signUpButton.setOnClickListener{

            if (emailEditText.text!!.isNotEmpty() && passwordEditText.text!!.isNotEmpty()){

                //TODO: Validar que es un email correcto
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener{

                            if(it.isSuccessful){

                                //Creamos un mensaje que indique que la autenticación ha ido bien
                                Toast.makeText(this, "Te has registrado correctamente:)", Toast.LENGTH_LONG).show()

                                showHome(it.result?.user?.email?: "", ProviderType.BASIC)
                            }else{

                                showAlert()
                            }
                }
            }
        }

        //Establecemos escuchador del botón de registro
        logInButton.setOnClickListener{

            if (emailEditText.text!!.isNotEmpty() && passwordEditText.text!!.isNotEmpty()){

                //TODO: Validar que es un email correcto
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener{

                    if(it.isSuccessful){

                        //Creamos un mensaje que indique que la autenticación ha ido bien
                        Toast.makeText(this, "Has hecho login correctamente", Toast.LENGTH_LONG).show()

                        //Llevamos al usuario a la pantalla home de la aplicación
                        showHome(it.result?.user?.email?: "", ProviderType.BASIC)

                    }else{

                        showAlert()
                    }
                }
            }
        }

        googleButton.setOnClickListener{

            //Configuración
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("Aceptar", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showHome(email: String, provider: ProviderType){

        //Mandamos la información de correo y provedor al HomeActivity
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }

        startActivity(homeIntent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {

                val account = task.getResult(ApiException::class.java)

                if(account != null){

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{

                        if(it.isSuccessful){

                            showHome(account.email?:"", ProviderType.GOOGLE)
                        }else{

                            showAlert()
                        }
                    }
                }
            }catch (e: ApiException){

                showAlert()
            }
        }
    }
}