package com.rivaldo.submissionintermediate.data.remote.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseGetDetailStories(
	val error: Boolean? = null,
	val message: String? = null,
	val story: Story? = null
) : Parcelable

@Parcelize
data class Story(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Float? = null,
	val id: String? = null,
	val lat: Float? = null
) : Parcelable
