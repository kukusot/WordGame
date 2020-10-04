package com.kukusot.wordgame.usecase

import androidx.annotation.VisibleForTesting
import com.kukusot.wordgame.domain.WordsRepository
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class GameManagerImpl @Inject constructor(
    private val questionFactory: QuestionFactory,
    private val wordsRepository: WordsRepository
) : GameManager {

    private val currentIndex = AtomicInteger(-1)
    private var questions: MutableList<GameQuestion> = arrayListOf()

    @VisibleForTesting
    fun setQuestions(questions: MutableList<GameQuestion>) {
        this.questions = questions
    }

    override fun hasNext(): Boolean {
        return questions.isNotEmpty() && currentIndex.get() < questions.size - 1
    }

    override fun nextQuestion(): GameQuestion {
        return questions[currentIndex.getAndIncrement()]
    }

    override suspend fun generateQuestions() {
        currentIndex.set(0)
        questions.apply {
            questions.clear()
            addAll(questionFactory.createQuestions(wordsRepository.getWords()))
        }
    }
}