package com.kukusot.wordgame.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kukusot.wordgame.R
import com.kukusot.wordgame.ui.data.AnswerState
import com.kukusot.wordgame.ui.data.GameState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_game.*

@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private var runningAnimation: Animator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.prepare()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGameButton.setOnClickListener {
            viewModel.startGame()
        }
        with(viewModel) {
            counterData.observe(viewLifecycleOwner, Observer {
                counterText.text = it
            })

            gameState.observe(viewLifecycleOwner, Observer { gameState ->
                when (gameState) {
                    is GameState.Loading -> {
                        showGameLoading()
                    }
                    is GameState.Ready -> {
                        showGameStartReady()
                    }
                    is GameState.Playing -> {
                        showGameIsPlaying(gameState)
                    }
                }
            })

            scoreData.observe(viewLifecycleOwner, Observer { state ->
                showScore(state.scoreText)
                when (state.answerState) {
                    AnswerState.CORRECT -> correctText.scaleOut()
                    AnswerState.WRONG -> wrongText.scaleOut()
                    else -> {
                        // Do nothing
                    }
                }
            })
        }

        wrongButton.setOnClickListener {
            cancelRunningAnimations()
            viewModel.answer(false)
        }

        correctButton.setOnClickListener {
            cancelRunningAnimations()
            viewModel.answer(true)
        }
    }

    private fun showScore(score: String) {
        scoreText.text = getString(R.string.score, score)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onGameInBackground()
        cancelRunningAnimations()
    }

    private fun showGameIsPlaying(state: GameState.Playing) {
        startGameButton.isVisible = false
        correctButton.isVisible = true
        wrongButton.isVisible = true
        spanishText.isVisible = true
        englishText.isVisible = true

        englishText.text = state.engText
        spanishText.text = state.spanishText

        runningAnimation = spanishText.fallInParent(parent)
    }

    private fun showGameStartReady() {
        progress.isVisible = false
        startGameButton.isVisible = true
    }

    private fun showGameLoading() {
        englishText.isVisible = false
        spanishText.isVisible = false
        correctButton.isVisible = false
        wrongButton.isVisible = false
        progress.isVisible = true
        startGameButton.isVisible = false
    }

    private fun cancelRunningAnimations() {
        runningAnimation?.apply {
            removeAllListeners()
            cancel()
        }
    }

}