package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import timber.log.Timber


class ShaderGLSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : GLSurfaceView(context, attrs) {

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        preserveEGLContextOnPause = true

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
        Timber.d("ShaderGLSurfaceView onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("ShaderGLSurfaceView onPause")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("ShaderGLSurfaceView onDetachedFromWindow")
    }
}