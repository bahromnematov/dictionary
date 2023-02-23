package uz.gita.mydictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.mydictionary.adapter.CursorAdapter
import uz.gita.mydictionary.database.MyDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database: MyDatabase
    private lateinit var adapter: CursorAdapter
    private lateinit var handle: Handler
    private var _query = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = MyDatabase.getDatabase()
        val cursor = database.getAllWords()
        adapter = CursorAdapter(cursor)

        val rv = findViewById<RecyclerView>(R.id.dictionaryList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        adapter.setChangeRememberStatusListener { id, newAmount ->
            database.updateWord(newAmount, id)
            adapter.cursor = database.getWordsByQuery(_query)
            adapter.notifyDataSetChanged()
        }

        handle = Handler(Looper.getMainLooper())
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnCloseListener {
            hideKeyboard()
            false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handle.removeCallbacksAndMessages(null)
                query?.let {
                    adapter.cursor = database.getWordsByQuery(it.trim())
                    adapter.query = it.trim()
                    _query = it.trim()
                    adapter.notifyDataSetChanged()
                }
                Log.d("TTT", "query = $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handle.removeCallbacksAndMessages(null)
                handle.postDelayed({
                    newText?.let {
                        adapter.cursor = database.getWordsByQuery(it.trim())
                        adapter.query = it.trim()
                        _query = it.trim()
                        adapter.notifyDataSetChanged()
                    }
                    Log.d("TTT", "newText = $newText")
                }, 1000)
                return true
            }
        })
    }
    private fun hideKeyboard():Boolean {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        return true
    }
}


