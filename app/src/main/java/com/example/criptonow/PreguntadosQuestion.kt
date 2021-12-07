package com.example.criptonow

import java.io.Serializable

/**Clase de datos utilizada para gestionar objetos de tipo pregunta del preguntados. Las inicio al principio para
 * poder tener la opción de crear objeto con esos valores por defecto.
 * LOS ATRIBUTOS 'CONTESTADA' Y 'ACERTADA' LOS PONGO DE TIPO VAR PORQUE POSTERIORMENTE NECESITARÉ MODIFICARLOS
 * PARA LLEVAR UN SEGUIMIENTO DEL ESTADO DE LAS PREGUNTAS EN MI APLICACIÓN**/
data class PreguntadosQuestion(val pregunta: String = "",
                               val rincorrecta1: String = "",
                               val rincorrecta2: String ="",
                               val rincorrecta3: String = "",
                               val rcorrecta: String = "",
                               val categoria: String = "",
                               val proyecto: String = "",
                               var contestada: Int = 0,
                               var acertada: Int = 0): Serializable{

}
