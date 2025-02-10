/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.byoutline.kickmaterial.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Build
import android.support.annotation.AnimRes
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.ImageView


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LUtils private constructor() {
    companion object {

        fun hasL() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        fun setStatusBarColor(activity: Activity, color: Int) {
            if (!hasL()) return

            activity.window.statusBarColor = color
        }

        fun toGrayscale(iv: ImageView) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)

            val filter = ColorMatrixColorFilter(matrix)
            iv.colorFilter = filter
        }

        @JvmOverloads
        fun loadAnimationWithLInterpolator(context: Context, @AnimRes animId: Int, interpolator: Interpolator = LinearOutSlowInInterpolator()): Animation {
            val animation = AnimationUtils.loadAnimation(context, animId)
            animation.interpolator = interpolator
            return animation
        }
    }
}
