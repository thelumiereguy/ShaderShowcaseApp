package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.math.MathUtils
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager.setSelectedShader
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader
import java.io.IOException
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun ShaderDetailPage(selectedShader: Shader, modifier: Modifier = Modifier) {

    val (snackBarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }

    val showMenu = remember {
        mutableStateOf(false)
    }

    val swipeableState = rememberSwipeableState(0) { state ->
        showMenu.value = state == 1
        true
    }

    val sizePx = with(LocalDensity.current) { 50.dp.toPx() }

    val anchors = mapOf(sizePx to 0, 0f to 1) // Maps anchor points (in px) to states

    Column(
        modifier = modifier
            .swipeable(
                swipeableState,
                anchors = anchors,
                orientation = Orientation.Vertical
            )
            .offset { IntOffset(0, (swipeableState.offset.value - sizePx).roundToInt()) }
    ) {
        val shaderView = remember {
            ShaderRenderer().apply {
                setShaders(
                    selectedShader.fragmentShader,
                    selectedShader.vertexShader,
                )
            }
        }

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
            AndroidView(factory = {
                ShaderGLSurfaceView(it)
            }) {
                it.setShaderRenderer(
                    shaderView
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            endY = 400f
                        )
                    )
            ) {

                Spacer(modifier = Modifier.height(60.dp))

                Icon(
                    painter = painterResource(id = com.thelumiereguy.shadershowcase.R.drawable.ic_double_down),
                    contentDescription = "Scroll to show or hide options",
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(
                            MathUtils.clamp(
                                map(
                                    swipeableState.offset.value,
                                    sizePx,
                                    0f,
                                    180f,
                                    0f
                                ),
                                0f,
                                180f
                            )
                        )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (showMenu.value) {
                        "Hide Option"
                    } else {
                        "Show Option"
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Surface(
            color = Color.Black,
            modifier = Modifier
                .requiredHeight(100.dp)
                .fillMaxWidth()
                .offset { IntOffset(0, (swipeableState.offset.value).roundToInt()) }
        ) {
            val context = LocalContext.current

            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    try {
                        coroutineScope.setSelectedShader(
                            context,
                            selectedShader.id
                        )

                        if (!ShaderShowcaseWallpaperService.isRunning())
                            context.openLiveWallpaperChooser()
                        else
                            setSnackBarState(!snackBarVisibleState)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }, modifier = Modifier
                    .padding(all = 16.dp)
                    .height(36.dp)
            ) {
                Text(text = "Set as LiveWallpaper")
            }

        }
    }

    if (snackBarVisibleState) {
        Snackbar(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "${selectedShader.title} set as Wallpaper!")
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailPagePreview() {
    ShaderDetailPage(Shader.getDefault())
}