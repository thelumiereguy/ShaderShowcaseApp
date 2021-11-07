package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader


@Composable
fun ListingCard(shader: Shader) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            AndroidView(factory = {
                ShaderGLSurfaceView(it)
            }) {
                it.setShader(
                    shader.fragmentShader
                )
            }

            Spacer(modifier = Modifier.graphicsLayer {

            })
        }
        Text(
            shader.title, modifier = Modifier
                .padding(all = 8.dp)
        )
    }
}

@Preview
@Composable
fun ListCardPreview() {
    ListingCard(Shader(fragmentShader = R.raw.starry_shimmer))
}