package com.example.app_notification_types.model

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)