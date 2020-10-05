package com.kukusot.wordgame.scores

import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsHighScoreRepository(private val prefs: SharedPreferences) : HighScoresRepository {

    private var currentScore = -1

    override fun modifyScore(diff: Int): Int {
        val score = getHighScore() + diff
        prefs.edit {
            putInt(HIGH_SCORE_NAME, score)
        }
        currentScore = score
        return currentScore
    }

    override fun getHighScore(): Int {
        if (currentScore == -1) {
            currentScore = prefs.getInt(HIGH_SCORE_NAME, 0)
        }
        return currentScore
    }

    companion object {
        const val HIGH_SCORE_NAME = "high_score"
    }
}