package com.rivaldo.submissionintermediate.utils

import com.rivaldo.submissionintermediate.domain.model.StoryModel

object DataDummy {
    fun generateDummyStoryEntity(): List<StoryModel> {
        val stories = ArrayList<StoryModel>()
        for (i in 0..10) {
            stories.add(
                StoryModel(
                    "https://picsum.photos/200/300",
                    "2021-08-01T09:00:00.000Z",
                    "Rivaldo $i",
                    "Ini adalah deskripsi dari story ini",
                    0.0f,
                    i.toString(),
                    0.0f
                )
            )
        }
        return stories
    }
}