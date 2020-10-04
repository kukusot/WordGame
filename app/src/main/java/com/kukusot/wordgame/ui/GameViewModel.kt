package com.kukusot.wordgame.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukusot.wordgame.usecase.GameManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel @ViewModelInject constructor(private val gameManager: GameManager) :
    ViewModel() {

    private var counterJob: Job? = null

    private val _counterData = MutableLiveData<String>()
    val counterData: LiveData<String> = _counterData

    fun startCounter() {
        counterJob?.cancel()

        var counter = 5
        counterJob = viewModelScope.launch {
            _counterData.value = counter.toString()
            while (counter > 0) {
                delay(1000)
                counter--
                _counterData.value = counter.toString()
            }
        }
    }

}