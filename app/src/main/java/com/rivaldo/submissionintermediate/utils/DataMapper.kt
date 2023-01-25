package com.rivaldo.submissionintermediate.utils

import com.rivaldo.submissionintermediate.data.remote.model.LoginResult
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.model.StandardModel

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
}