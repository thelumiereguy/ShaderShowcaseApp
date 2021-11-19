package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable.GLShader
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.shader_details_page.presentation.to
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable.ShaderDetailOptionsBottomSheet
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable.SwipeIcon
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.helper.getButtonColorPair
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ShaderDetailPage(
    selectedShader: Shader,
    modifier: Modifier = Modifier,
) {

    val swipeableState = rememberSwipeableState(0)

    val showMenu = derivedStateOf {
        swipeableState.currentValue == 1
    }

    val bottomSheetHeight = 240.dp

    val sizePx = with(LocalDensity.current) { bottomSheetHeight.toPx() }

    val anchors = mapOf(sizePx to 0, 0f to 1) // Maps anchor points (in px) to states

    val coroutineScope = rememberCoroutineScope()

    val shaderRenderer = remember {
        ShaderRenderer()
    }

    var buttonColorsPair by rememberSaveable(
        key = selectedShader.id.toString()
    ) {
        mutableStateOf(
            android.graphics.Color.BLACK
                    to
                    android.graphics.Color.WHITE
        )
    }


    LaunchedEffect(key1 = selectedShader, block = {
        shaderRenderer.setShaders(
            selectedShader.fragmentShader,
            selectedShader.vertexShader,
            "Detail Listing Page ${selectedShader.title}"
        )

        shaderRenderer.getButtonColorPair(coroutineScope) { buttonColorPair ->
            buttonColorsPair = buttonColorPair
        }
    })

    Box(
        modifier = modifier
            .swipeable(
                swipeableState,
                anchors = anchors,
                orientation = Orientation.Vertical
            )
    ) {
        GLShader(
            renderer = shaderRenderer,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        )


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        0,
                        (swipeableState.offset.value).roundToInt()
                    )
                }
        ) {

            val interactionSource = remember {
                MutableInteractionSource()
            }

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
                                Color.Black.copy(alpha = 0.9f)
                            ),
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

                Spacer(modifier = Modifier.height(10.dp))
            }

            ShaderDetailOptionsBottomSheet(
                selectedShader,
                buttonColorsPair,
                modifier = Modifier.requiredHeight(bottomSheetHeight)
            )

        }
    }

//            if (snackBarVisibleState) {
//                Snackbar(
//                    modifier = Modifier.padding(8.dp)
//                ) {
//                    Text(text = "${selectedShader.title} set as Wallpaper!")
//                }
//            }
}


@ExperimentalPagerApi
@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailPagePreview() {
    ShaderDetailPage(Shader.getDefault())
}
