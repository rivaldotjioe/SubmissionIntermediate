package com.rivaldo.submissionintermediate.utils

import com.rivaldo.submissionintermediate.data.remote.model.ListStoryItem
import com.rivaldo.submissionintermediate.data.remote.model.LoginResult
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import com.rivaldo.submissionintermediate.domain.model.StoryModel

object DataMapper {

    fun ResponseStandard?.toStandardModel(): StandardModel {
        return StandardModel(
            message = this?.message
        )
    }

    fun LoginResult.toLoginModel(): LoginModel {
        return LoginModel(
            name = this.name as String,
            userId = this.userId as String,
            token = this.token as String
        )
    }

    fun ListStoryItem.toStoryModel(): StoryModel {
        return StoryModel(
            photoUrl = this.photoUrl as String,
            createdAt = this.createdAt as String,
            name = this.name as String,
            description = this.description as String,
            lon = this.lon,
            id = this.id as String,
            lat = this.lat
        )
    }
    fun List<ListStoryItem>.toStoryModel(): List<StoryModel> {
        val list = ArrayList<StoryModel>()
        this.map {
            list.add(it.toStoryModel())
        }
        return list
    }
}