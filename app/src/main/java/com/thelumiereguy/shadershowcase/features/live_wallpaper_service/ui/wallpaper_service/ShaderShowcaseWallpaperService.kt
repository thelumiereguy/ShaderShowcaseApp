package com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.wallpaper_service;

import android.app.ActivityManager
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.thelumiereguy.shadershowcase.core.data.local.PreferenceManager
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.view.LiveWallpaperGLSurfaceView
import com.thelumiereguy.shadershowcase.features.live_wallpaper_service.ui.view.LiveWallpaperShaderRenderer
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber

class ShaderShowcaseWallpaperService : WallpaperService() {


    companion object {
        private var wallpaperEngine: WallpaperEngine? = null

        fun isRunning() = wallpaperEngine != null
    }

    override fun onCreateEngine(): Engine {
        return WallpaperEngine().also { engineInstance ->
            wallpaperEngine = engineInstance
        }
    }

    inner class WallpaperEngine : WallpaperService.Engine(), LifecycleOwner {

        private var glSurfaceView: LiveWallpaperGLSurfaceView? = null
        private var rendererHasBeenSet = false

        private var shaderRenderer: ShaderRenderer? = null

        private val shaders by lazy {
            ShaderFactory.getShadersList(applicationContext)
        }

        private val activityManager =
            getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        private val configurationInfo =
            activityManager?.deviceConfigurationInfo
        private val supportsEs2 = configurationInfo?.reqGlEsVersion ?: 0 >= 0x20000


        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            observeSelectedShaderChanges()
            Timber.d("WallpaperEngine onCreateEngine $isPreview")
            setSurfaceView(surfaceHolder)
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }


        private fun setSurfaceView(holder: SurfaceHolder?) {
            Timber.d("WallpaperEngine setSurfaceView $holder")
            glSurfaceView = object : LiveWallpaperGLSurfaceView(applicationContext) {
                override fun getSurfaceViewHolder(): SurfaceHolder? = holder.apply {
                    this?.setFormat(
                        PixelFormat.RGBA_8888
                    )
                }
            }

            if (supportsEs2) {
                lifecycleScope.launch(Dispatchers.Main.immediate) {
                    glSurfaceView?.setEGLContextClientVersion(2)
                    glSurfaceView?.preserveEGLContextOnPause = true

                    val selectedShaderId =
                        PreferenceManager.getSelectedShader(applicationContext).firstOrNull()

                    selectedShaderId?.let { id ->
                        val frag = shaders[id].fragmentShader
                        val vertex = shaders[id].vertexShader

                        if (frag.isNotEmpty() && vertex.isNotEmpty())
                            setRenderer(
                                LiveWallpaperShaderRenderer(
                                    frag,
                                    vertex,
                                    applicationContext
                                ).also {
                                    shaderRenderer = it
                                }
                            )
                    }

                }
            } else {
                return
            }
        }

        private fun observeSelectedShaderChanges() {
            lifecycleScope.launchWhenStarted {
                PreferenceManager.getSelectedShader(applicationContext).collect {
                    val newShader = shaders[it]

                    Timber.d("observeSelectedShaderChanges   $it")
                    val newFragShader = newShader.fragmentShader
                    val newVertexShader = newShader.vertexShader
                    shaderRenderer?.prepareProgram(newFragShader, newVertexShader)
                }

            }
        }


        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            Timber.d("WallpaperEngine onVisibilityChanged $visible")
            if (visible) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            } else {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }
            if (rendererHasBeenSet) {
                if (visible) {
                    glSurfaceView?.onResume()
                } else {
                    glSurfaceView?.onPause()
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            Timber.d("WallpaperEngine onDestroy")
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            glSurfaceView?.onDestroy()
//            shaderRenderer?.release()
        }

        private fun setRenderer(renderer: GLSurfaceView.Renderer) {
            glSurfaceView?.setShaderRenderer(renderer)
            rendererHasBeenSet = true
        }

        private val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = lifecycleRegistry
    }
}