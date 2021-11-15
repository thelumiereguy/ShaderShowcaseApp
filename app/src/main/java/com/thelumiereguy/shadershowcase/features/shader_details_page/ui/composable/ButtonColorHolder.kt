package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonColorHolder(
    val backgroundColor: Int,
    val textColor: Int,
) : Parcelable