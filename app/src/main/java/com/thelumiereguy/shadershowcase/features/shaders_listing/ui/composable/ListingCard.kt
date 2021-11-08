package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader


@Composable
fun ListingCard(shader: Shader, onShaderSelected: (Shader) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .clickable {
                onShaderSelected(shader)
            },
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            AndroidView(factory = {
                ShaderGLSurfaceView(it)
            }) {
                it.setShader(
                    shader.fragmentShader
                )
            }

            Column(
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

                Text(
                    shader.title,
                    color = Color(0xFFEBDBC1),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                )

                Text(
                    shader.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFFEBDBC1),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun ListCardPreview() {
    ShaderShowcaseTheme {
        ListingCard(
            Shader.getDefault()
        ) {


        }
    }
}