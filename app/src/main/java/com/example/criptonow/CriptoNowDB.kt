package com.example.criptonow

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.system.Os.close
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import org.w3c.dom.NamedNodeMap
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.sql.SQLException

class CriptoNowDB(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    /*Clase que gestiona la base de datos interna de la aplicación*/

    companion object{
        private val DB_NAME = "criptonowdb.db"
        private val DB_VERSION = 1
    }

    fun openDatabase(): SQLiteDatabase {

        val dbFile = context.getDatabasePath(DB_NAME)


        try{
            val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)
            checkDB?.close()
            copyDatabase(dbFile)

        }catch (e: Exception){

            e.printStackTrace()
        }

        return SQLiteDatabase.openDatabase(dbFile.path,null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File){

        val openDB = context.assets.open(DB_NAME)
        val outPutStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while(openDB.read(buffer)>0){

            outPutStream.write(buffer)
            Log.d("DB","writing")
        }

        outPutStream.flush()
        outPutStream.close()
        openDB.close()

        Log.d("DB","completed")
    }

    @Throws(SQLException::class)
    fun FireQuery(query: String): Cursor? {

        var TempCursor: Cursor? = null
        val database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)

        try{

            TempCursor = database.rawQuery(query, null)
            if(TempCursor != null && TempCursor.count > 0){

                if(TempCursor.moveToFirst()){

                    return TempCursor
                }
            }

        }catch (e: Exception){

            e.printStackTrace()
        }finally {

            database?.close()
        }

        return TempCursor
    }

    fun modificarInsignia(nombre: String, imagen: String): Int {

        val args = arrayOf(nombre)

        val datos = ContentValues()
        datos.put("imagen",imagen)


        val db = this.writableDatabase
        return db.update("INSIGNIAS",datos,"nombre = ?", arrayOf(nombre))

        db.close()

    }


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE Persons (\n" +
                "    PersonID int,\n" +
                "    LastName varchar(255),\n" +
                "    FirstName varchar(255),\n" +
                "    Address varchar(255),\n" +
                "    City varchar(255)\n" +
                ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}