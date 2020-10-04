package com.kukusot.wordgame.sound

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import com.kukusot.wordgame.R

class SoundManager(
    private val context: Context,
    private val audioManager: AudioManager,
    private val soundPool: SoundPool
) {

    private val winId = soundPool.load(context, R.raw.small_win, 1)


    fun playWin() {
        val volume = getPlayVolume()
        soundPool.play(winId, volume, volume, 1, 0, 1.0f)
    }

    private fun getPlayVolume(): Float {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return currentVolume * 1.0f / maxVolume
    }
}