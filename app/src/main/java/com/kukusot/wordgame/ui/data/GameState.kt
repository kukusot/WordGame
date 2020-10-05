package com.kukusot.wordgame.ui.data

sealed class GameState {
    object Loading : GameState()
    object Ready : GameState()
    data class Playing(val engText: String, val spanishText: String) : GameState()
}