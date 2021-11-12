package com.example.criptonow

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.sql.SQLException

class CriptoNowDB (private val context: Context){
    /*Clase que gestiona la base de datos interna de la aplicación*/

    companion object{
        private val DB_NAME = "criptonowdb.db"
    }

    fun openDatabase(): SQLiteDatabase {

        val dbFile = context.getDatabasePath(DB_NAME)

        try{
            val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)
            checkDB.close()
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

}