package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer

import android.graphics.Bitmap
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import androidx.palette.graphics.Palette
import timber.log.Timber
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.roundToInt

open class ShaderRenderer : GLSurfaceView.Renderer {

    private val positionComponentCount = 2

    private val tableVertices by lazy {
        floatArrayOf(
            0f, 0f,
            -1f, -1f,
            1f, -1f,
            1f, 1f,
            -1f, 1f,
            -1f, -1f
        )
    }

    private val bytesPerFloat = 4

    private val data by lazy {
        ByteBuffer.allocateDirect(tableVertices.size * bytesPerFloat)
            .order(ByteOrder.nativeOrder())
    }

    private val verticesData by lazy {
        data.asFloatBuffer().also {
            it.put(tableVertices)
        }
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        glDisable(GL10.GL_DITHER)
        glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
    }

    private val isProgramChanged = AtomicBoolean(false)

    var programId: Int? = null

    private lateinit var fragmentShader: String
    private lateinit var vertexShader: String

    fun setShaders(fragmentShader: String, vertexShader: String) {
        this.fragmentShader = fragmentShader
        this.vertexShader = vertexShader
        isProgramChanged.compareAndSet(false, true)
    }

    private fun setupProgram() {
        programId?.let { glDeleteProgram(it) }

        programId = glCreateProgram().also { newProgramId ->
            if (programId == 0) {
                Timber.d("Could not create new program")
                return
            }

            val fragShader = createAndVerifyShader(fragmentShader, GL_FRAGMENT_SHADER)
            val vertShader = createAndVerifyShader(vertexShader, GL_VERTEX_SHADER)

            glAttachShader(newProgramId, vertShader)
            glAttachShader(newProgramId, fragShader)

            glLinkProgram(newProgramId)

            val linkStatus = IntArray(1)
            glGetProgramiv(newProgramId, GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] == 0) {
                glDeleteProgram(newProgramId)
                Timber.d("Linking of program failed. ${glGetProgramInfoLog(newProgramId)}")
                return
            }

            if (validateProgram(newProgramId)) {
                positionAttributeLocation = glGetAttribLocation(newProgramId, "a_Position")
                resolutionUniformLocation = glGetUniformLocation(newProgramId, "u_resolution")
                timeUniformLocation = glGetUniformLocation(newProgramId, "u_time")
            } else {
                Timber.d("Validating of program failed.");
                return
            }

            verticesData.position(0)

            positionAttributeLocation?.let { attribLocation ->
                glVertexAttribPointer(
                    attribLocation,
                    positionComponentCount,
                    GL_FLOAT,
                    false,
                    0,
                    verticesData
                )

                glEnableVertexAttribArray(attribLocation)
            }

            glUseProgram(newProgramId)

            glDetachShader(newProgramId, vertShader)
            glDetachShader(newProgramId, fragShader)
            glDeleteShader(vertShader)
            glDeleteShader(fragShader)
        }
    }

    private var positionAttributeLocation: Int? = null
    private var resolutionUniformLocation: Int? = null
    private var timeUniformLocation: Int? = null

    private var surfaceHeight = 0f
    private var surfaceWidth = 0f

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        surfaceWidth = width.toFloat()
        surfaceHeight = height.toFloat()
        frameCount = 0f
    }

    private var frameCount = 0f

    override fun onDrawFrame(gl: GL10?) {
        glDisable(GL10.GL_DITHER)
        glClear(GL10.GL_COLOR_BUFFER_BIT)

        if (isProgramChanged.getAndSet(false)) {
            setupProgram()
        }

        resolutionUniformLocation?.let {
            glUniform2f(it, surfaceWidth, surfaceHeight)
        }

        timeUniformLocation?.let {
            glUniform1f(it, frameCount)
        }

        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)

        getPaletteCallback?.let { callback ->
            getCurrentBitmap()?.let { bitmap ->
                Palette.Builder(bitmap).generate {
                    it?.let { palette ->
                        callback(palette)
                        bitmap.recycle()
                        getPaletteCallback = null
                    }
                }
            }
        }

        glFinish()

        if (frameCount > 25) {
            frameCount = 0f
        }

        frameCount += 0.01f
    }

    private fun getCurrentBitmap(): Bitmap? {
        val data = ByteBuffer.allocateDirect(
            surfaceWidth.roundToInt() *
                    surfaceHeight.roundToInt() * bytesPerFloat
        ).order(ByteOrder.nativeOrder())

        glReadPixels(
            0,
            0,
            surfaceWidth.roundToInt(),
            surfaceHeight.roundToInt(),
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            data
        )

        val bitmap = Bitmap.createBitmap(
            surfaceWidth.roundToInt(),
            surfaceHeight.roundToInt(),
            Bitmap.Config.ARGB_8888
        )

        bitmap.copyPixelsFromBuffer(data)
        return bitmap
    }

    private fun validateProgram(programObjectId: Int): Boolean {
        glValidateProgram(programObjectId)
        val validateStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)

        Timber.tag("Results of validating").v(
            "${validateStatus[0]} \n  Log : ${
                glGetProgramInfoLog(
                    programObjectId
                )
            } \n".trimIndent()
        )

        return validateStatus[0] != 0
    }

    private var getPaletteCallback: ((Palette) -> Unit)? = null

    fun setPaletteCallback(callback: (Palette) -> Unit) {
        getPaletteCallback = callback
    }

}
