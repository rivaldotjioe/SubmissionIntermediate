package com.rivaldo.core.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseStandard(
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable
