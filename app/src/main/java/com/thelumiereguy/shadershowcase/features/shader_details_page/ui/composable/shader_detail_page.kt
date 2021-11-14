package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable.GLShader
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ShaderDetailPage(
    selectedShader: Shader,
    modifier: Modifier = Modifier
) {

    val swipeableState = rememberSwipeableState(0)

    val showMenu = derivedStateOf {
        swipeableState.currentValue == 1
    }

    val sizePx = with(LocalDensity.current) { 100.dp.toPx() }

    val anchors = mapOf(sizePx to 0, 0f to 1) // Maps anchor points (in px) to states

    val coroutineScope = rememberCoroutineScope()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val shaderRenderer = remember {
        ShaderRenderer().apply {
            setShaders(
                selectedShader.fragmentShader,
                selectedShader.vertexShader,
            )
        }
    }

    var buttonColors by rememberSaveable(
        key = selectedShader.title
    ) {
        mutableStateOf(Color.Black to Color.White)
    }

    LaunchedEffect(key1 = shaderRenderer, block = {
        shaderRenderer.getButtonColorPair { buttonColorPair ->
            buttonColors = buttonColorPair
        }
    })

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .swipeable(
                    swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Vertical
                )
                .offset { IntOffset(0, (swipeableState.offset.value - sizePx).roundToInt()) }
        ) {

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .offset {
                        IntOffset(
                            0,
                            ((swipeableState.offset.value - sizePx) * 0.9f).roundToInt()
                        )
                    }
            ) {

                GLShader(
                    renderer = shaderRenderer
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource,
                            null
                        ) {
                            coroutineScope.launch {
                                if (showMenu.value) {
                                    swipeableState.animateTo(0)
                                } else {
                                    swipeableState.animateTo(1)
                                }
                            }
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                endY = 400f
                            )
                        )
                ) {

                    Spacer(modifier = Modifier.height(60.dp))


                    SwipeIcon(
                        showMenu,
                        swipeableState,
                        sizePx
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        color = PrimaryTextColor,
                        text = if (showMenu.value) {
                            "Hide Option"
                        } else {
                            "Show Option"
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            ShaderDetailOptionsBottomSheet(
                offset = IntOffset(
                    0,
                    (swipeableState.offset.value).roundToInt()
                ),
                selectedShader,
                buttonColors
            )

//            if (snackBarVisibleState) {
//                Snackbar(
//                    modifier = Modifier.padding(8.dp)
//                ) {
//                    Text(text = "${selectedShader.title} set as Wallpaper!")
//                }
//            }

        }
    }
}


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailPagePreview() {
    ShaderDetailPage(Shader.getDefault())
}