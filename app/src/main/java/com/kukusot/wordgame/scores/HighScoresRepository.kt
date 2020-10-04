package com.kukusot.wordgame.scores

interface HighScoresRepository {

    fun modifyScore(diff: Int): Int

    fun getHighScore(): Int
}