package uz.gita.mydictionary.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.mydictionary.R
import uz.gita.mydictionary.extensions.inflate
import uz.gita.mydictionary.extensions.spannable
import uz.gita.mydictionary.model.DictionaryData

class CursorAdapter(var cursor: Cursor, var query: String = "") :
    RecyclerView.Adapter<CursorAdapter.MyViewHolder>() {
    private var changeRememberStatusListener: ((Int, Int) -> Unit)? = null

    @SuppressLint("Range")
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = itemView.findViewById<TextView>(R.id.title)
        private val subTitle = itemView.findViewById<TextView>(R.id.subTitle)
        private val bookmark = itemView.findViewById<ImageView>(R.id.bookmark)

        init {
            bookmark.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                val oldAmount = cursor.getInt(cursor.getColumnIndex("isRemember"))
                val id = cursor.getInt(cursor.getColumnIndex("id"))

                if (oldAmount == 1) {
                    changeRememberStatusListener?.invoke(id, 0)
                    bookmark.setImageResource(R.drawable.ic_bookmark)
                }else {
                    bookmark.setImageResource(R.drawable.ic_bookmark_select)
                    changeRememberStatusListener?.invoke(id, 1)
                }
            }
        }

        fun bind(data: DictionaryData) {
            if (query == "") textTitle.text = data.word
            else textTitle.text = data.word.spannable(query)
            subTitle.text = data.definition
            if (data.isRemember == 0) {
                bookmark.setImageResource(R.drawable.ic_bookmark)
            } else bookmark.setImageResource(R.drawable.ic_bookmark_select)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.inflate(R.layout.item_dictionary))
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val data = DictionaryData(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("word")),
            cursor.getString(cursor.getColumnIndex("wordtype")),
            cursor.getString(cursor.getColumnIndex("definition")),
            cursor.getInt(cursor.getColumnIndex("isRemember"))
        )
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

     fun setChangeRememberStatusListener(block:(Int,Int)->Unit){
        changeRememberStatusListener = block
    }
}