package com.example.criptonow
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

public class Badge constructor(title: String, detail: String, image: String){
    /*Clase que construye objetos de la clase insignia (title, description, image)*/

    //Atributos de las insignias
    var title:String = "";
    var detail:String = "";
    var image:String = "";

    fun printData(){
        /*Imprime los datos de la insignia*/
        var datos = "Título: $title \nDetalle: $detail \nImagen:$image"
        println(datos)
    }

}