package com.kukusot.wordgame.usecase

interface GameManager {

    fun hasNext(): Boolean

    fun nextQuestion(): GameQuestion

    suspend fun generateQuestions()
}