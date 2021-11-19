package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService
import com.thelumiereguy.shadershowcase.features.shader_details_page.presentation.ButtonColorHolder
import com.thelumiereguy.shadershowcase.features.shader_details_page.presentation.ButtonState
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.helper.openLiveWallpaperChooser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedButton(
    buttonColors: ButtonColorHolder,
    shaderId: Int,
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val preferenceManager = remember {
        PreferenceManager(context)
    }

    val selectedShader = preferenceManager.getSelectedShader().collectAsState(initial = -1)

    val buttonBackgroundColor = animateColorAsState(
        Color(buttonColors.backgroundColor),
        animationSpec = tween(500)
    )


    val buttonTextColor = animateColorAsState(
        Color(buttonColors.textColor),
        animationSpec = tween(500)
    )

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = buttonBackgroundColor.value) {
        val shouldUseDarkIcons = buttonBackgroundColor.value.luminance() >= 0.5f
        systemUiController.setStatusBarColor(
            buttonBackgroundColor.value,
            shouldUseDarkIcons
        )
    }

    DisposableEffect(key1 = true, effect = {
        onDispose {
            preferenceManager.release()
        }
    })

    var buttonState by remember {
        mutableStateOf(
            ButtonState()
        )
    }

    val wallpaperServiceRunning by derivedStateOf {
        ShaderShowcaseWallpaperService.isRunning(
            context
        )
    }

    /**
     * Logic for deriving button state
     */
    LaunchedEffect(key1 = shaderId, key2 = selectedShader.value, key3 = wallpaperServiceRunning) {
        buttonState = ButtonState(
            showSuccessText = selectedShader.value == shaderId && wallpaperServiceRunning
        )
    }

    val transition =
        updateTransition(
            targetState = buttonState,
            label = "Button Alpha and size transition"
        )

    val buttonAlpha by transition.animateFloat(
        transitionSpec = {
            tween(
                400,
                easing = FastOutSlowInEasing
            )
        },
        targetValueByState = { state ->
            if (state.showLoading)
                0f
            else
                1f
        }, label = "Alpha"
    )

    val buttonWidth by transition.animateDp(
        transitionSpec = {
            tween(
                400,
                easing = FastOutSlowInEasing
            )
        },
        targetValueByState = { state ->
            if (state.showLoading)
                0.dp
            else
                270.dp
        }, label = "Width"
    )


    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonBackgroundColor.value,
            ),
            border = BorderStroke(
                0.5.dp,
                PrimaryTextColor
            ),
            onClick = {
                if (!buttonState.showSuccessText) {
                    coroutineScope.launch {
                        if (!wallpaperServiceRunning) {
                            preferenceManager.setSelectedShader(shaderId)
                            context.openLiveWallpaperChooser()
                        } else {
                            buttonState = ButtonState(showLoading = true)
                            delay(500)
                            preferenceManager.setSelectedShader(shaderId)
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(all = 8.dp)
                .requiredHeight(48.dp)
                .alpha(buttonAlpha)
                .width(buttonWidth)

        ) {

            when {
                buttonState.isDefaultState -> {
                    Text(
                        text = "Set as Live Wallpaper",
                        color = buttonTextColor.value,
                        fontWeight = FontWeight.Bold
                    )
                }
                buttonState.showSuccessText -> {
                    Icon(
                        Icons.Filled.Check,
                        "Tick icon",
                        tint = buttonTextColor.value.copy(alpha = 0.7f),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Wallpaper Set",
                        color = buttonTextColor.value,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = buttonState.showLoading,
            enter = fadeIn(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            CircularProgressIndicator(
                color = PrimaryTextColor
            )
        }
    }
}

@Preview
@Composable
fun AnimatedButtonPreview() {
    ShaderShowcaseTheme {
        AnimatedButton(
            buttonColors = ButtonColorHolder(0, 0),
            0
        )
    }
}