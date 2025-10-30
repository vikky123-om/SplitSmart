package com.example.splitsmart.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Data class to represent the request body for registration
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val is_admin: Boolean = false
)

// Define your API endpoints here
interface ApiService {

    // POST request to /register endpoint in FastAPI
    @POST("register")
    fun registerUser(@Body request: RegisterRequest): Call<ResponseBody>
}