package com.example.app_notification_types.api

import com.example.app_notification_types.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenService {
    @FormUrlEncoded
    @POST("token")
    fun login(
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<TokenResponse>
}