package com.kukusot.wordgame.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.addListener

private const val FALL_DURATION = 5000L

fun View.fallInParent(parent: View, endAction: () -> Unit): Animator {
    return ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, parent.height.toFloat()).apply {
        duration = FALL_DURATION
        interpolator = AccelerateInterpolator()
        addListener(onEnd = {
            endAction()
        })
        start()
    }
}