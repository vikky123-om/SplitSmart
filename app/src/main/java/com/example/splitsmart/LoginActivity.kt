package com.example.splitsmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import com.example.splitsmart.network.ApiClient
import com.example.splitsmart.network.ApiService
import com.example.splitsmart.network.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val phoneEditText = findViewById<TextInputEditText>(R.id.phone_edit_text)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().trim()

            if (phoneNumber.length == 10) {
                // Call the backend API
                val api = ApiClient.retrofit.create(ApiService::class.java)
                val request = RegisterRequest(
                    username = phoneNumber, // use phone as username for now
                    email = "$phoneNumber@gmail.com",
                    password = "default123"
                )

                api.registerUser(request).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "✅ Registered Successfully!", Toast.LENGTH_SHORT).show()
                            navigateToMainScreen()
                        } else {
                            Toast.makeText(this@LoginActivity, "❌ Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                            Log.e("API", "Error: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "⚠️ Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        Log.e("API", "Failure: ${t.message}")
                    }
                })
            } else {
                phoneEditText.error = "Please enter a valid 10-digit phone number."
            }
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}