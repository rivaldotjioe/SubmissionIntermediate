package com.rivaldo.submissionintermediate.data.remote.api

import com.rivaldo.submissionintermediate.data.remote.model.ResponseGetAllStories
import com.rivaldo.submissionintermediate.data.remote.model.ResponseGetDetailStories
import com.rivaldo.submissionintermediate.data.remote.model.ResponseLogin
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseStandard

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseLogin

    @POST("stories")
    suspend fun addNewStories(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "multipart/form-data",
        @Field("description") title: String,
        @Body photo: MultipartBody.Part,
        @Field("lat") lat: Float? = null,
        @Field("lon") lon: Float? = null,
    ) : ResponseStandard

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
    ) : ResponseGetAllStories

    @GET("stories/{id}")
    suspend fun getStoriesById(
        @Header("Authorization") token: String,
        @Field("id") id: String
    ) : ResponseGetDetailStories
}