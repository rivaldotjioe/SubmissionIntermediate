package com.rivaldo.core.utils

import com.rivaldo.core.data.local.entity.StoryEntity
import com.rivaldo.core.data.remote.model.ListStoryItem
import com.rivaldo.core.data.remote.model.LoginResult
import com.rivaldo.core.data.remote.model.ResponseStandard
import com.rivaldo.core.domain.model.LoginModel
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.model.StoryModel

object DataMapper {

    fun ResponseStandard?.toStandardModel(): StandardModel {
        return StandardModel(
            error = this?.error,
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
    @JvmName("toStoryModelListStoryItem")
    fun List<ListStoryItem>.toStoryModel(): List<StoryModel> {
        val list = ArrayList<StoryModel>()
        this.map {
            list.add(it.toStoryModel())
        }
        return list
    }

    fun StoryEntity.toStoryModel(): StoryModel {
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

    fun List<StoryEntity>.toStoryModel() : List<StoryModel> {
        val list = ArrayList<StoryModel>()
        this.map {
            list.add(it.toStoryModel())
        }
        return list
    }

    fun StoryModel.toStoryEntity() : StoryEntity {
        return StoryEntity(
            id = this.id as String,
            name = this.name as String,
            photoUrl = this.photoUrl as String,
            createdAt = this.createdAt as String,
            description = this.description as String,
            lon = this.lon,
            lat = this.lat
        )
    }

    fun List<StoryModel>.toStoryEntity(): List<StoryEntity> {
        val list = ArrayList<StoryEntity>()
        this.map {
            list.add(it.toStoryEntity())
        }
        return list
    }
}