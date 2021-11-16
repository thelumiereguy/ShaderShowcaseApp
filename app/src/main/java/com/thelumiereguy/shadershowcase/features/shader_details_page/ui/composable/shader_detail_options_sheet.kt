package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService
import java.io.IOException

@Composable
fun ShaderDetailOptionsBottomSheet(
    selectedShader: Shader,
    buttonColors: ButtonColorHolder,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val preferenceManager = remember {
        PreferenceManager(context)
    }

    DisposableEffect(key1 = true, effect = {
        onDispose {
            preferenceManager.release()
        }
    })


    Surface(
        color = Color.Black.copy(alpha = 0.9f),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
        ) {

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
                    backgroundColor = Color(buttonColors.backgroundColor),
                ),
                border = BorderStroke(
                    0.5.dp,
                    Color.White
                ),
                onClick = {
                    try {
                        preferenceManager.run {
                            coroutineScope.setSelectedShader(
                                selectedShader.id
                            )
                        }

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
                    color = Color(buttonColors.textColor),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }

    }
}