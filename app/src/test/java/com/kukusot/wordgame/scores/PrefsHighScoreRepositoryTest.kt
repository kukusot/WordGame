package com.kukusot.wordgame.scores

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test

class PrefsHighScoreRepositoryTest {

    private val prefs: SharedPreferences = mock()
    private val repository: HighScoresRepository = PrefsHighScoreRepository(prefs)

    private val initialScoreValue = 100


    @Test
    fun getHighScore_should_return_correct_score() {
        mockSavedScore()
        val scoreDff = -50
        val editor: SharedPreferences.Editor = mock()
        whenever(prefs.edit()).thenReturn(editor)

        assertEquals(initialScoreValue, repository.getHighScore())

        repository.modifyScore(scoreDff)
        assertEquals(initialScoreValue + scoreDff, repository.getHighScore())
    }

    @Test
    fun modifyHighScore_should_add_the_diff_and_save_it_and_return_modified_score() {
        mockSavedScore()
        val scoreDiff = 10
        val editor: SharedPreferences.Editor = mock()
        whenever(prefs.edit()).thenReturn(editor)
        val targetScore = initialScoreValue + scoreDiff

        val result = repository.modifyScore(scoreDiff)

        assertEquals(targetScore, result)
        verify(editor).putInt(PrefsHighScoreRepository.HIGH_SCORE_NAME, targetScore)
        verify(editor).apply()
    }

    private fun mockSavedScore() {
        whenever(prefs.getInt(PrefsHighScoreRepository.HIGH_SCORE_NAME, 0)).thenReturn(
            initialScoreValue
        )
    }
}