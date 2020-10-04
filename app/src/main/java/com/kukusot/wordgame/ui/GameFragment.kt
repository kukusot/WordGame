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
import com.kukusot.wordgame.sound.SoundManager
import com.kukusot.wordgame.ui.data.AnswerState
import com.kukusot.wordgame.ui.data.GameState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    @Inject
    lateinit var soundManager: SoundManager
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
                scoreText.text = getString(R.string.score, state.scoreText)
                when (state.answerState) {
                    AnswerState.CORRECT -> {
                        correctText.scaleOut()
                        scoreText.bounceAround()
                        soundManager.playWin()
                    }
                    AnswerState.WRONG -> wrongText.scaleOut()
                    else -> {
                        // Do nothing
                    }
                }
            })
        }

        wrongButton.setOnClickListener {
            runningAnimation?.cancel()
            viewModel.answer(false)
        }

        correctButton.setOnClickListener {
            runningAnimation?.cancel()
            viewModel.answer(true)
        }
    }

    override fun onPause() {
        super.onPause()
        runningAnimation?.cancel()
        viewModel.onGameInBackground()
    }

    private fun showGameIsPlaying(state: GameState.Playing) {
        startGameButton.isVisible = false
        gameControls.isVisible = true

        englishText.text = state.engText
        spanishText.text = state.spanishText

        runningAnimation = spanishText.fallInParent(parent)
    }

    private fun showGameStartReady() {
        progress.isVisible = false
        gameControls.isVisible = false
        startGameButton.isVisible = true
    }

    private fun showGameLoading() {
        progress.isVisible = true
        gameControls.isVisible = false
        startGameButton.isVisible = false
    }

}