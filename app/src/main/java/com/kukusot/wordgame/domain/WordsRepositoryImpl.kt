package com.kukusot.wordgame.domain

import com.kukusot.wordgame.data.WordsDataSource

class WordsRepositoryImpl(private val wordsDataSource: WordsDataSource) :
    WordsRepository {

    private var cachedWords: List<Word>? = null

    override suspend fun getWords(): List<Word> {
        if (cachedWords == null) {
            fetchWords()
        }
        return cachedWords!!
    }

    private suspend fun fetchWords() {
        cachedWords = wordsDataSource.getWords().map {
            Word(it.englishText, it.spanishText)
        }
    }
}