package com.thelumiereguy.shadershowcase.features.shader_details_page.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonColorHolder(
    val backgroundColor: Int,
    val textColor: Int,
) : Parcelable

infix fun Int.to(textColor: Int): ButtonColorHolder {
    return ButtonColorHolder(this, textColor)
}