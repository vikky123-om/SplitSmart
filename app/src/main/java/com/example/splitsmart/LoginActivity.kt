package com.example.splitsmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

/**
 * This Activity handles the initial user login/sign-up via phone number.
 * For this initial version, it performs basic 10-digit validation and then navigates
 * to the main app screen (MainActivity).
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout file to use for this screen
        setContentView(R.layout.activity_login)

        // Find the UI elements by their IDs defined in activity_login.xml
        val phoneEditText = findViewById<TextInputEditText>(R.id.phone_edit_text)
        val loginButton = findViewById<Button>(R.id.login_button)

        // Set a click listener for the main login button
        loginButton.setOnClickListener {
            // 1. Get the text input and remove any leading/trailing spaces
            val phoneNumber = phoneEditText.text.toString().trim()

            // 2. Perform a basic validation check: needs exactly 10 digits
            if (phoneNumber.length == 10) {
                // If validation passes, show a success message and proceed
                Toast.makeText(this, "Logged in as $phoneNumber", Toast.LENGTH_SHORT).show()
                navigateToMainScreen()
            } else {
                // If validation fails, show an error directly on the input field
                phoneEditText.error = "Please enter a valid 10-digit phone number."
            }
        }
    }

    /**
     * Navigates the user to the MainActivity (the main bill splitting screen).
     */
    private fun navigateToMainScreen() {
        // Intent is used to switch from one screen (Activity) to another
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Finish the LoginActivity so the user cannot press the back button to return to login
        finish()
    }
}
