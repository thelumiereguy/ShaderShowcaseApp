package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer

import android.opengl.GLES20
import timber.log.Timber

internal fun createAndVerifyShader(shaderCode: String, shaderType: Int): Int {
    val shaderId = GLES20.glCreateShader(shaderType)
    if (shaderId == 0) {
        Timber.d("Create Shader failed")
    }

    GLES20.glShaderSource(shaderId, shaderCode)
    GLES20.glCompileShader(shaderId)

    val compileStatusArray = IntArray(1)
    GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatusArray, 0)
    Timber.d("$shaderCode \n : ${GLES20.glGetShaderInfoLog(shaderId)}")

    if (compileStatusArray.first() == 0) {
        GLES20.glDeleteShader(shaderId)
        return 0
    }

    return shaderId
}