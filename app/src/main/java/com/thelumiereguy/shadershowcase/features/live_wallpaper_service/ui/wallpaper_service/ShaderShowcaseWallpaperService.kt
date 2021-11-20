package com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service

import android.app.ActivityManager
import android.app.WallpaperManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.lifecycle.*
import com.thelumiereguy.shadershowcase.core.data.ShaderFactory
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.view.LiveWallpaperGLSurfaceView
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


class ShaderShowcaseWallpaperService : WallpaperService() {

    companion object {
        fun isRunning(context: Context): Boolean {
            val wpm = WallpaperManager.getInstance(context)
            val info = wpm.wallpaperInfo
            return info != null && info.packageName == context.packageName
        }
    }

    override fun onCreateEngine(): Engine {
        return WallpaperEngine()
    }

    inner class WallpaperEngine : WallpaperService.Engine(), LifecycleOwner {

        private var glSurfaceView: LiveWallpaperGLSurfaceView? = null
        private var rendererHasBeenSet = false

        private var shaderRenderer: ShaderRenderer? = null

        private var preferenceManager: PreferenceManager? = null

        private val activityManager =
            applicationContext.getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        private val configurationInfo =
            activityManager?.deviceConfigurationInfo
        private val supportsEs2 = configurationInfo?.reqGlEsVersion ?: 0 >= 0x20000


        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            preferenceManager = PreferenceManager(applicationContext)
            observeSelectedShaderChanges()
            setSurfaceView(surfaceHolder)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        private var selectedShader: Shader? = null

        private fun setSurfaceView(holder: SurfaceHolder?) {
            if (supportsEs2) {
                glSurfaceView = object : LiveWallpaperGLSurfaceView(applicationContext) {
                    override fun getSurfaceViewHolder(): SurfaceHolder? = holder
                }

                glSurfaceView?.setEGLContextClientVersion(2)
                glSurfaceView?.preserveEGLContextOnPause = true

                setRenderer(
                    ShaderRenderer(
                    ).also {
                        shaderRenderer = it

                        setShaderToRenderer()
                    }
                )
            }
        }

        private fun setShaderToRenderer() {
            val shader = selectedShader
            if (shader != null) {
                shaderRenderer?.setShaders(
                    shader.fragmentShader,
                    shader.vertexShader,
                    "LiveWallpaper ${shader.title}"
                )
            }
        }

        private fun observeSelectedShaderChanges() {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    preferenceManager?.getSelectedShader()
                        ?.filter {
                            it != -1
                        }?.onEach { index ->
                            val shadersList = ShaderFactory.getShadersList(applicationContext)
                            val newShader = shadersList[index]
                            selectedShader = newShader
                            glSurfaceView ?: kotlin.run {
                                Timber.d("SurfaceView is null. Initializing it")
                                setSurfaceView(surfaceHolder)
                            }
                            setShaderToRenderer()
                        }?.collect()
                }
            }
        }


        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            } else {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }
            if (visible) {
                glSurfaceView?.onResume()
            } else {
                glSurfaceView?.onPause()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            if (isPreview) {
                stopSelf()
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                glSurfaceView?.onPause()
                glSurfaceView = null
                shaderRenderer = null
                preferenceManager?.release()
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            glSurfaceView?.onPause()
            glSurfaceView?.onDestroy()
            glSurfaceView = null
            shaderRenderer = null
            preferenceManager?.release()
        }

        private fun setRenderer(renderer: GLSurfaceView.Renderer) {
            glSurfaceView?.setShaderRenderer(renderer)
            rendererHasBeenSet = true
        }

        private val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = lifecycleRegistry
    }
}