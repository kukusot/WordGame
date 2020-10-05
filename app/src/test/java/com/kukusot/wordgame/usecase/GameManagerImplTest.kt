package com.kukusot.wordgame.usecase

import com.kukusot.wordgame.domain.Word
import com.kukusot.wordgame.domain.WordsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GameManagerImplTest {

    private val questionFactory: QuestionFactory = mock()
    private val wordsRepository: WordsRepository = mock()

    private val gameManager: GameManagerImpl = GameManagerImpl(questionFactory, wordsRepository)

    @Test
    fun `generateQuestions should populate questions`() = runBlocking {
        val mockWords = createMockWords()
        val mockQuestions = createMockQuestions()
        whenever(wordsRepository.getWords()).thenReturn(mockWords)
        whenever(questionFactory.createQuestions(mockWords)).thenReturn(mockQuestions)

        gameManager.generateQuestions()

        assertTrue(gameManager.hasNext())
        assertEquals(mockQuestions[0], gameManager.nextQuestion())
    }

    @Test
    fun `hasNext should be false when generated questions are empty`() = runBlocking {
        val mockWords = createMockWords()
        val mockQuestions = emptyList<GameQuestion>()
        whenever(wordsRepository.getWords()).thenReturn(mockWords)
        whenever(questionFactory.createQuestions(mockWords)).thenReturn(mockQuestions)

        gameManager.generateQuestions()

        assertFalse(gameManager.hasNext())
    }

    @Test
    fun `hasNext should be false when question index is same as questions size`() = runBlocking {
        val mockWords = createMockWords()
        val mockQuestions = createMockQuestions()
        whenever(wordsRepository.getWords()).thenReturn(mockWords)
        whenever(questionFactory.createQuestions(mockWords)).thenReturn(mockQuestions)

        gameManager.generateQuestions()
        repeat(mockQuestions.size) {
            gameManager.nextQuestion()
        }

        assertFalse(gameManager.hasNext())
    }


    private fun createMockWords() = arrayListOf(
        Word("eng1", "spa1"),
        Word("eng2", "spa2")
    )

    private fun createMockQuestions() =
        arrayListOf(
            GameQuestion("eng1", "spa1", true),
            GameQuestion("eng2", "spa2", false)
        )
}