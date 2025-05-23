package org.jellyfin.androidtv.ui.background

import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.data.service.BackgroundService
import org.koin.compose.koinInject

@Composable
private fun AppThemeBackground() {
    val context = LocalContext.current
    val themeBackground = remember(context.theme) {
        val attributes = context.theme.obtainStyledAttributes(intArrayOf(R.attr.defaultBackground))
        val drawable = attributes.getDrawable(0)
        attributes.recycle()

        if (drawable is ColorDrawable) drawable.toBitmap(1, 1).asImageBitmap()
        else drawable?.toBitmap()?.asImageBitmap()
    }

    if (themeBackground != null) {
        Image(
            bitmap = themeBackground,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
    }
}

@Composable
fun AppBackground() {
    val backgroundService = koinInject<BackgroundService>()
    val currentBackground by backgroundService.currentBackground.collectAsState()
    val blurBackground by backgroundService.blurBackground.collectAsState()
    val blurIntensity by backgroundService.blurIntensity.collectAsState()
    val enabled by backgroundService.enabled.collectAsState()
    val dimmingIntensity by backgroundService.backdropDimmingIntensity.collectAsState()

    // More detailed logging
    Log.e("AppBackground", "Enabled: $enabled")
    Log.e("AppBackground", "Current Background: $currentBackground")
    Log.e("AppBackground", "Blur Background: $blurBackground")
    Log.e("AppBackground", "Blur Intensity: $blurIntensity")
    Log.e("AppBackground", "Dimming Intensity (raw): $dimmingIntensity")
    Log.e("AppBackground", "Dimming Intensity (applied): $dimmingIntensity")

    // Add a fallback for when background is not enabled
    if (!enabled) {
        Log.e("AppBackground", "Background is NOT enabled!")
        AppThemeBackground()
        return
    }

    AnimatedContent(
        targetState = currentBackground,
        transitionSpec = {
            val duration = (BackgroundService.TRANSITION_DURATION.inWholeMilliseconds / 2).toInt()
            fadeIn(tween(durationMillis = duration)) togetherWith fadeOut(snap(delayMillis = duration))
        },
        label = "BackgroundTransition",
    ) { background ->
        if (background != null) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    bitmap = background,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        colorResource(R.color.background_filter).copy(
                            alpha = dimmingIntensity  // Use directly
                        ), 
                        BlendMode.SrcAtop
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (blurBackground) Modifier.blur((blurIntensity * 20).dp) else Modifier)
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(
                                alpha = dimmingIntensity  // Use directly
                            )
                        )
                )
            }
        } else {
            Log.e("AppBackground", "Background is NULL, using AppThemeBackground")
            AppThemeBackground()
        }
    }
}