package com.kukusot.wordgame.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kukusot.wordgame.scores.HighScoresRepository
import com.kukusot.wordgame.ui.data.AnswerState
import com.kukusot.wordgame.ui.data.GameState
import com.kukusot.wordgame.ui.data.ScoreState
import com.kukusot.wordgame.usecase.GameManager
import com.kukusot.wordgame.usecase.GameQuestion
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val gameManager: GameManager = mock()
    private val scoreRepository: HighScoresRepository = mock()
    private val scoreObserver: Observer<ScoreState> = mock()
    private val counterObserver: Observer<String> = mock()
    private val gameStateObserver: Observer<GameState> = mock()

    private val equalQuestion = GameQuestion("eng", "spa", true)
    private val notEqualQuestion = GameQuestion("eng", "spa", false)

    private val viewModel = GameViewModel(gameManager, scoreRepository)

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel.scoreData.observeForever(scoreObserver)
        viewModel.counterData.observeForever(counterObserver)
        viewModel.gameState.observeForever(gameStateObserver)
    }

    @Test
    fun `prepare should generate questions and set initial score value`() {
        runBlocking {
            val highScore = 10;
            whenever(scoreRepository.getHighScore()).thenReturn(highScore)

            viewModel.prepare()

            inOrder(gameStateObserver) {
                gameStateObserver.onChanged(GameState.Loading)
                gameStateObserver.onChanged(GameState.Ready)
            }

            verify(gameManager).generateQuestions()
            verify(scoreObserver).onChanged(ScoreState(highScore.toString(), AnswerState.NO_ANSWER))
        }
    }

    @Test
    fun `answer should add won points and post correct state when answer is correct`() {
        val modifiedScore = 200
        val wordsAreEqual = true
        viewModel.currentQuestion = GameQuestion("eng", "spa", wordsAreEqual)
        whenever(scoreRepository.modifyScore(GameViewModel.WON_POINTS_PER_QUESTION)).thenReturn(
            modifiedScore
        )

        viewModel.answer(wordsAreEqual)

        verify(scoreRepository).modifyScore(GameViewModel.WON_POINTS_PER_QUESTION)
        verify(scoreObserver).onChanged(ScoreState(modifiedScore.toString(), AnswerState.CORRECT))
    }

    @Test
    fun `answer should add lost points and post wrong state when answer is wrong`() {
        val modifiedScore = 200
        val wordsAreEqual = true
        viewModel.currentQuestion = GameQuestion("eng", "spa", wordsAreEqual)
        whenever(scoreRepository.modifyScore(GameViewModel.LOST_POINTS_PER_QUESTION)).thenReturn(
            modifiedScore
        )

        viewModel.answer(!wordsAreEqual)

        verify(scoreRepository).modifyScore(GameViewModel.LOST_POINTS_PER_QUESTION)
        verify(scoreObserver).onChanged(ScoreState(modifiedScore.toString(), AnswerState.WRONG))
    }

    @Test
    fun `startGame should post playingState when gameManager has next question`() {
        mockNextQuestion(equalQuestion)

        viewModel.startGame()

        verify(gameStateObserver).onChanged(
            GameState.Playing(
                equalQuestion.engText,
                equalQuestion.spanishText
            )
        )
    }

    @Test
    fun `onGameInBackground should set game state to ready`() {
        viewModel.onGameInBackground()

        verify(gameStateObserver).onChanged(GameState.Ready)
    }

    private fun mockNextQuestion(question: GameQuestion) {
        whenever(gameManager.hasNext()).thenReturn(true)
        whenever(gameManager.nextQuestion()).thenReturn(question)
    }

}