package com.kukusot.wordgame.data

import com.google.gson.annotations.SerializedName

data class WordDto(
    @SerializedName("text_eng") val englishText: String,
    @SerializedName("text_spa") val spanishText: String
)