package com.kukusot.wordgame.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible

private const val FALL_DURATION = 5000L
private const val ANIMATION_SHORT = 300L

fun View.fallInParent(parent: View): Animator {
    return ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, parent.height.toFloat()).apply {
        duration = FALL_DURATION
        interpolator = AccelerateInterpolator()
        start()
    }
}

fun View.scaleOut() {
    isVisible = true
    val alpha = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0f, 0.0f)
    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1.0f, 2.0f)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.0f, 2.0f)
    AnimatorSet().apply {
        playTogether(alpha, scaleX, scaleY)
        duration = ANIMATION_SHORT
        addListener(doOnEnd {
            isVisible = false
        })
    }.start()
}