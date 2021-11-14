package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager.setSelectedShader
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService
import java.io.IOException

@Composable
fun ShaderDetailOptionsBottomSheet(
    selectedShader: Shader,
    buttonColors: Pair<Color, Color>,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Black.copy(alpha = 0.68f),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {
            val context = LocalContext.current

            val coroutineScope = rememberCoroutineScope()

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

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColors.first,
                ),
                border = BorderStroke(
                    0.5.dp,
                    Color.White
                ),
                onClick = {
                    try {
                        coroutineScope.setSelectedShader(
                            context,
                            selectedShader.id
                        )

                        if (!ShaderShowcaseWallpaperService.isRunning())
                            context.openLiveWallpaperChooser()
//                            setSnackBarState(!snackBarVisibleState)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                },
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
            ) {
                Text(
                    text = "Set as Live Wallpaper",
                    color = buttonColors.second,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
        }

    }
}