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
private const val BOUNCE_DURATION = 500L

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

//Copy paste code from a game I have developed before, did not bother to refactor it.
fun View.bounceAround() {
    val set = AnimatorSet()
    set.duration = BOUNCE_DURATION
    val oneWidth = width * 1.0f / 100
    val oneHeight = height * 1.0f / 100
    set.playTogether(
        ObjectAnimator.ofFloat(this, View.SCALE_X, 1.4f, 1.4f, 1.3f, 1f),
        ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.4f, 1.4f, 1.3f, 1f),
        ObjectAnimator.ofFloat(
            this,
            View.TRANSLATION_X,
            0f,
            3f * oneWidth,
            -2.5f * oneWidth,
            1.7f * oneWidth,
            -1.25f * oneWidth,
            1.1f * oneWidth,
            0f * oneWidth,
            0f
        ),
        ObjectAnimator.ofFloat(
            this,
            View.TRANSLATION_Y,
            0f * oneHeight,
            -5f * oneHeight,
            5f * oneHeight,
            -3f * oneHeight,
            3f * oneHeight,
            1.4f * oneHeight,
            0f * oneHeight,
            0f
        )
    );
    set.start();
}
