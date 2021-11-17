package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.helper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.palette.graphics.Palette
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun Context.openLiveWallpaperChooser() {
    Intent().let { intent ->
        intent.action = WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(
                this,
                ShaderShowcaseWallpaperService::class.java
            )
        )
        startActivity(intent)
    }
}

internal fun map(
    value: Float,
    startRangeMin: Float,
    startRangeMax: Float,
    endRangeMin: Float,
    endRangeMax: Float
): Float {
    return (value - startRangeMin) / (startRangeMax - startRangeMin) * (endRangeMax - endRangeMin) + endRangeMin
}

internal fun ShaderRenderer.getButtonColorPair(
    coroutineScope: CoroutineScope,
    callback: (ButtonColorHolder) -> Unit
) {
    coroutineScope.launch {
        delay(500)
        setPaletteCallback { palette ->
            getColor(palette, callback)
        }
    }
}

private fun getColor(
    palette: Palette,
    callback: (ButtonColorHolder) -> Unit
) {
    (palette.lightVibrantSwatch ?: palette.lightMutedSwatch ?: palette.dominantSwatch
    ?: palette.vibrantSwatch
    ?: palette.darkVibrantSwatch)?.let { swatch ->
        val buttonBackgroundColor = swatch.rgb
        val buttonTextColor = swatch.bodyTextColor
        callback(ButtonColorHolder(buttonBackgroundColor, buttonTextColor))
    } ?: run {
        callback(ButtonColorHolder(Color.BLACK, Color.WHITE))
    }
}

internal infix fun Int.to(textColor: Int): ButtonColorHolder {
    return ButtonColorHolder(this, textColor)
}