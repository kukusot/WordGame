package com.kukusot.wordgame.usecase

data class GameQuestion(
    val engText: String,
    val spanishText: String,
    val wordsAreEqual: Boolean
)