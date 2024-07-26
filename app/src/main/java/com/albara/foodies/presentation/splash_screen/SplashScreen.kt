package com.albara.foodies.presentation.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.albara.foodies.R
import com.albara.foodies.presentation.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigate : () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen_animation))
    val logoAnimationState = animateLottieCompositionAsState(composition = composition)
    LaunchedEffect(key1 = true) {
        delay(3000L)
        onNavigate()
    }


    Box(
        Modifier
            .fillMaxSize()
            .background(Orange),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = {logoAnimationState.progress}
        )
    }

}