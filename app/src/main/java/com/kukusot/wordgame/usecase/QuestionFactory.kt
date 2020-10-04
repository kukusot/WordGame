package com.kukusot.wordgame.usecase

import com.kukusot.wordgame.domain.Word

interface QuestionFactory {

    suspend fun createQuestions(words: List<Word>): List<GameQuestion>
}