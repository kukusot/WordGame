package com.kukusot.wordgame.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kukusot.wordgame.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.counterData.observe(viewLifecycleOwner, Observer {
            counterText.text = it
        })

        correctButton.setOnClickListener {
            cancelRunningAnimations()
            runningAnimation = spanishText.fallInParent(parent) {
            }
            viewModel.startCounter()
        }
    }

    private fun cancelRunningAnimations() {
        runningAnimation?.apply {
            removeAllListeners()
            cancel()
        }
    }

}