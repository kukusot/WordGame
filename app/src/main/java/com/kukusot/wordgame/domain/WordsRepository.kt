package com.kukusot.wordgame.domain

interface WordsRepository {

    suspend fun getWords(): List<Word>
}