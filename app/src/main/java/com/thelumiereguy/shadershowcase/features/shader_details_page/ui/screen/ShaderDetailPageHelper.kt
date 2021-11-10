package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service.ShaderShowcaseWallpaperService

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
    return (value - startRangeMin) / (startRangeMax - startRangeMin) * (endRangeMax - endRangeMin) + endRangeMin;
}
