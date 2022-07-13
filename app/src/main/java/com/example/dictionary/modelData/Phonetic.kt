package com.example.dictionary.modelData

import com.google.gson.annotations.SerializedName

data class Phonetic(
    @SerializedName("audio")
    val audio: String,
    val license: LicenseX,
    val sourceUrl: String,
    @SerializedName("text")
    val text: String
)