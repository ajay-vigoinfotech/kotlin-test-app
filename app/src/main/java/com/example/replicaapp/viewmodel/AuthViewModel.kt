package com.example.replicaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.replicaapp.api.ApiClient
import com.example.replicaapp.model.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private val _tokenResponse = MutableLiveData<TokenResponse>()
    val tokenResponse: LiveData<TokenResponse> = _tokenResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(grantType: String, username: String, password: String) {
        _loading.postValue(true) // Start loading

        val service = ApiClient.tokenService
        service.login(grantType, username, password).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    _tokenResponse.postValue(response.body())
                    _loading.postValue(false)
                } else {
                    // Safely parse error body
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorJson = org.json.JSONObject(it)
                            val errorDescription = errorJson.optString("error_description", "")
                            val errorParam = errorJson.optString("error", "")
                            val combinedMessage = when {
                                errorDescription.isNotEmpty() -> errorDescription
                                errorParam.isNotEmpty() -> errorParam
                                else -> "Unknown Error"
                            }
                            _error.postValue(combinedMessage)
                        } catch (e: Exception) {
                            _error.postValue("Login failed with unknown error format")
                        }
                    } ?: run {
                        _error.postValue("Login failed: No error body")
                    }
                    _loading.postValue(false)
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                _loading.postValue(false) // Stop loading
                _error.postValue("Error: ${t.message}")
            }
        })
    }
}