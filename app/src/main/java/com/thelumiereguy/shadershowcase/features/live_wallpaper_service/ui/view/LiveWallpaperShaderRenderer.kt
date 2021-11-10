package com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.view

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class LiveWallpaperShaderRenderer(
    fragmentShader: String,
    vertexShader: String,
    private var context: Context?,
) : ShaderRenderer() {

    private val shaders by lazy {
        ShaderFactory.getShadersList(context!!)
    }



    fun release() {
        context = null
    }

}
