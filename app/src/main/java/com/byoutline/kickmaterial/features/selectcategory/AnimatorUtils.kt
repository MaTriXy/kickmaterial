package com.byoutline.kickmaterial.features.selectcategory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimatorUtils {
    fun getScaleAnimator(view: View, startScale: Float, endScale: Float): AnimatorSet {
        val set = AnimatorSet()
        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, startScale, endScale)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, startScale, endScale)
        set.playTogether(scaleXAnimator, scaleYAnimator)
        return set
    }
}
