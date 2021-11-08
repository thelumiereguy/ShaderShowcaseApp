package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen
//
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.offset
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import com.google.accompanist.insets.ProvideWindowInsets
//import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
//import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader
//import dev.chrisbanes.snapper.ExperimentalSnapperApi
//import kotlin.math.roundToInt
//
//@ExperimentalMaterialApi
//@Composable
//fun ShaderDetailListing(selectedShader: Shader, onBackPressed: () -> Unit) {
//    ProvideWindowInsets {
//
//        val showMenu by remember {
//            mutableStateOf(false)
//        }
//
//        val squareSize = 48.dp
//
//        val swipeableState = rememberSwipeableState(0)
//        val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//        val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states
//
//        Column(
//            modifier = Modifier.swipeable(
//                swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.3f) },
//                orientation = Orientation.Vertical
//            )
//        ) {
//            Box(
//                contentAlignment = Alignment.TopEnd
//            ) {
//                AndroidView(factory = {
//                    ShaderGLSurfaceView(it)
//                }) {
//                    it.setShader(
//                        selectedShader.fragmentShader
//                    )
//                }
//            }
//
//            Surface(
//                color = Color.Red,
//                modifier = Modifier.offset {
//                    IntOffset(
//                        0,
//                        swipeableState.offset.value.roundToInt()
//                    )
//                }) {
//
//            }
//        }
//
//    }
//}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Preview
//@Composable
//fun ShaderDetailListingPreview() {
//    ShaderDetailPage(Shader.getDefault()) {}
//}