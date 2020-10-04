package com.kukusot.wordgame.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WordsLocalDataSource constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : WordsDataSource {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getWords(): List<WordDto> {
        val inputStream = assetManager.open(WORDS_FILE_NAME)
        return gson.fromJson(inputStream.reader(), wordListType)
    }

    companion object {
        const val WORDS_FILE_NAME = "words_v2.json"
        private val wordListType = object : TypeToken<List<WordDto>>() {}.type

    }
}