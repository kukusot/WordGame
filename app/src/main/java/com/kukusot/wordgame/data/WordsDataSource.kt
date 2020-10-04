package com.kukusot.wordgame.data

interface WordsDataSource {

    suspend fun getWords(): List<WordDto>
}