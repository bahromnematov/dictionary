package uz.gita.mydictionary.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView

class MyDatabase private constructor(context: Context) : DBHelper(context, "Dictionary.db", 2) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var database: MyDatabase? = null
        fun init(context: Context) {
            database = MyDatabase(context)
        }
        fun getDatabase():MyDatabase = database!!
    }

    fun getAllWords():Cursor{
        return database().rawQuery("select * from entries",null)
    }

    fun getWordsByQuery(query:String):Cursor{
        return database().rawQuery("select * from entries where entries.word like '%$query%'",null)

    }

    fun updateWord(remember:Int,id:Int){
        val cv = ContentValues()
        cv.put("isRemember",remember)
        database().update("entries",cv,"entries.id = $id",null)
    }
}