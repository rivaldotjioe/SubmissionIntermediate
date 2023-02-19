package com.rivaldo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel(
    val name: String,
    val userId: String,
    val token: String
) : Parcelable
