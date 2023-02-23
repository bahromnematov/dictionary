package uz.gita.mydictionary.app

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import uz.gita.mydictionary.database.MyDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        MyDatabase.init(this)
    }
    companion object{
        lateinit var instance: Context
        private set
    }
}