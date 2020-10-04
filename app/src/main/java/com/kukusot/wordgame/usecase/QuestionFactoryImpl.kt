package com.kukusot.wordgame.usecase

import com.kukusot.wordgame.domain.Word
import com.kukusot.wordgame.domain.WordsRepository
import javax.inject.Inject
import kotlin.random.Random

class QuestionFactoryImpl @Inject constructor(private val repository: WordsRepository) : QuestionFactory {

    override suspend fun createQuestions(words: List<Word>): List<GameQuestion> {
        val gameWords = mutableListOf<Word>().apply {
            addAll(repository.getWords())
            shuffle()
        }

        return gameWords.mapIndexed { index, word ->
            val shouldBeCorrect = shouldAnswerBeCorrect()
            if (shouldBeCorrect) {
                GameQuestion(word.valueEng, word.valueSpanish, true)
            } else {
                val randomWord = words[getRandomIndex(index, words.size - 1)]
                GameQuestion(word.valueEng, randomWord.valueSpanish, false)
            }
        }
    }

    private fun getRandomIndex(currentIndex: Int, maxValue: Int): Int {
        var randomIndex = currentIndex
        while (randomIndex == currentIndex) {
            randomIndex = Random.nextInt(0, maxValue)
        }
        return randomIndex
    }

    private fun shouldAnswerBeCorrect(): Boolean {
        val random = Random.nextInt(100)
        return random < CORRECT_ANSWER_PERCENTAGE
    }

    companion object {
        const val CORRECT_ANSWER_PERCENTAGE = 30
    }
}