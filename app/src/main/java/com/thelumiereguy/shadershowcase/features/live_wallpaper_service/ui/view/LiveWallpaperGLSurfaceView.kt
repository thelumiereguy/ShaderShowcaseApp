package com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.view

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.SurfaceHolder
import timber.log.Timber


abstract class LiveWallpaperGLSurfaceView constructor(
    context: Context,
) : GLSurfaceView(context) {

    init {
        id = generateViewId()
    }

    private var hasSetShader = false

    fun setShaderRenderer(
        renderer: Renderer
    ) {
        if (hasSetShader.not())
            setRenderer(
                renderer
            )

        hasSetShader = true
    }

    override fun onResume() {
        super.onResume()
        renderMode = RENDERMODE_CONTINUOUSLY
        Timber.d("LiveWallpaperGLSurfaceView onResume")
    }

    override fun onPause() {
        renderMode = RENDERMODE_WHEN_DIRTY
        super.onPause()
        Timber.d("LiveWallpaperGLSurfaceView onPause")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("LiveWallpaperGLSurfaceView onDetachedFromWindow")
    }

    override fun getHolder(): SurfaceHolder {
        return getSurfaceViewHolder() ?: super.getHolder()
    }

    abstract fun getSurfaceViewHolder(): SurfaceHolder?

    fun onDestroy() {
        super.onDetachedFromWindow()
    }
}