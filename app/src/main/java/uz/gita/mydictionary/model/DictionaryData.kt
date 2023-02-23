package uz.gita.mydictionary.model

data class DictionaryData(
    val id: Int,
    val word: String,
    val wordType: String,
    val definition: String,
    var isRemember: Int
)

