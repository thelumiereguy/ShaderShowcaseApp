package com.thelumiereguy.shadershowcase.features.shader_details_page.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonState(
    val showLoading: Boolean = false,
    val showSuccessText: Boolean = false
) : Parcelable {
    val isDefaultState: Boolean
        get() = !(showLoading || showSuccessText)
}
