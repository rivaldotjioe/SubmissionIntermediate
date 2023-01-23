package com.rivaldo.submissionintermediate.utils

import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.model.StandardModel

object DataMapper {

    fun ResponseStandard?.toStandardModel(): StandardModel {
        return StandardModel(
            message = this?.message
        )
    }
}