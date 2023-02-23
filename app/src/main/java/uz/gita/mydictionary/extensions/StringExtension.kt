package uz.gita.mydictionary.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import uz.gita.mydictionary.R
import uz.gita.mydictionary.app.App

fun String.spannable(query:String):SpannableString{
    val span = SpannableString(this)
    val color = ForegroundColorSpan(ContextCompat.getColor(App.instance, R.color.purple_500))
    val startIndex = this.indexOf(query,0,true)
    if (startIndex>-1){
        span.setSpan(color,startIndex,startIndex+query.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return span
}
