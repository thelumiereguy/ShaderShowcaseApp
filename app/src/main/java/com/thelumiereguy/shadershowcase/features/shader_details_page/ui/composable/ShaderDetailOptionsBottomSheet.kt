package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.shader_details_page.presentation.ButtonColorHolder

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShaderDetailOptionsBottomSheet(
    selectedShader: Shader,
    buttonColors: ButtonColorHolder,
    modifier: Modifier = Modifier
) {

    Surface(
        color = Color.Black.copy(alpha = 0.9f),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {

            Text(
                selectedShader.title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
            )

            Text(
                selectedShader.description,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )

            AnimatedButton(
                buttonColors,
                selectedShader.id,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
fun BottomSheetPreview() {
    ShaderShowcaseTheme() {
        ShaderDetailOptionsBottomSheet(
            selectedShader = Shader.getDefault(),
            buttonColors = ButtonColorHolder(0, 0)
        )
    }
}