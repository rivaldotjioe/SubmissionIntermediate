package com.rivaldo.core.data.remote.api

import com.rivaldo.core.data.remote.model.ResponseGetAllStories
import com.rivaldo.core.data.remote.model.ResponseGetDetailStories
import com.rivaldo.core.data.remote.model.ResponseLogin
import com.rivaldo.core.data.remote.model.ResponseStandard
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @Multipart
    @POST("stories")
    suspend fun addNewStories(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
    ) : ResponseStandard

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
    ) : ResponseGetAllStories

    @GET("stories")
    suspend fun getAllStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ) : ResponseGetAllStories
    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : ResponseGetAllStories

    @GET("stories/{id}")
    suspend fun getStoriesById(
        @Header("Authorization") token: String,
        @Field("id") id: String
    ) : ResponseGetDetailStories
}