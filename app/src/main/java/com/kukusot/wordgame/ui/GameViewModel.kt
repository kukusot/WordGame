package com.kukusot.wordgame.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukusot.wordgame.scores.HighScoresRepository
import com.kukusot.wordgame.ui.data.AnswerState
import com.kukusot.wordgame.ui.data.GameState
import com.kukusot.wordgame.ui.data.ScoreState
import com.kukusot.wordgame.usecase.GameManager
import com.kukusot.wordgame.usecase.GameQuestion
import kotlinx.coroutines.*

class GameViewModel @ViewModelInject constructor(
    private val gameManager: GameManager,
    private val scoresRepository: HighScoresRepository
) :
    ViewModel() {

    private var counterJob: Job? = null
    internal var currentQuestion: GameQuestion? = null

    private val _counterData = MutableLiveData<String>()
    val counterData: LiveData<String> = _counterData

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    private val _scoreData = MutableLiveData<ScoreState>()
    var scoreData: LiveData<ScoreState> = _scoreData

    fun prepare() {
        viewModelScope.launch {
            _gameState.value = GameState.Loading
            withContext(Dispatchers.IO) {
                gameManager.generateQuestions()
            }
            _gameState.value = GameState.Ready
            _scoreData.value =
                ScoreState(scoresRepository.getHighScore().toString(), AnswerState.NO_ANSWER)
        }
    }

    fun startGame() {
        goToNextQuestion()
    }

    fun answer(wordsAreEqual: Boolean) {
        currentQuestion?.let { question ->
            if (wordsAreEqual == question.wordsAreEqual) {
                onAnswer(AnswerState.CORRECT)
            } else {
                onAnswer(AnswerState.WRONG)
            }
        }
    }

    private fun onAnswer(state: AnswerState) {
        val pointsDiff = if (state == AnswerState.CORRECT) {
            WON_POINTS_PER_QUESTION
        } else {
            LOST_POINTS_PER_QUESTION
        }

        val currentScore = scoresRepository.modifyScore(pointsDiff)
        _scoreData.value = ScoreState(currentScore.toString(), state)
        goToNextQuestion()
    }

    private fun goToNextQuestion() {
        counterJob?.cancel()
        if (gameManager.hasNext()) {
            currentQuestion = gameManager.nextQuestion()
            currentQuestion?.let {
                _gameState.value = GameState.Playing(it.engText, it.spanishText)
            }
            startCounter()
        }
    }

    fun onGameInBackground() {
        counterJob?.cancel()
        _gameState.value = GameState.Ready
    }

    private fun startCounter() {
        counterJob?.cancel()

        var counter = DROP_SECONDS
        counterJob = viewModelScope.launch {
            _counterData.value = counter.toString()
            while (counter > 0) {
                delay(1000)
                counter--
                _counterData.value = counter.toString()
            }
            onAnswer(AnswerState.NO_ANSWER)
        }
    }

    companion object {
        const val WON_POINTS_PER_QUESTION = 1
        const val LOST_POINTS_PER_QUESTION = -2
        const val DROP_SECONDS = 5
    }

}