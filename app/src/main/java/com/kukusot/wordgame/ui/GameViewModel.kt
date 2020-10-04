package com.kukusot.wordgame.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kukusot.wordgame.usecase.GameManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameViewModel @ViewModelInject constructor(private val gameManager: GameManager) :
    ViewModel() {

    init {
        GlobalScope.launch {
            val questions = gameManager.generateQuestions()
        }
    }

}