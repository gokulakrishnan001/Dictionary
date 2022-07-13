package com.example.dictionary.modelData

import com.google.gson.annotations.SerializedName

data class DictModelItem(
    val license: License?,
    @SerializedName("meanings")
    val meanings: List<Meaning>?,
    @SerializedName("phonetics")
    val phonetics: List<Phonetic>?,
    val sourceUrls: List<String>?,
    @SerializedName("word")
    val word: String?
)
