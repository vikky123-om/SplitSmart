package com.example.splitsmart

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 101
    private val REQUEST_IMAGE_CAPTURE = 1

    // A flag to simulate whether the user has groups or not (for the Groups tab)
    private val hasGroups = true // TRUE to show the list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()

        // 1. Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        // Load the initial fragment (Groups tab)
        if (savedInstanceState == null) {
            val initialFragment = if (hasGroups) GroupsListFragment() else EmptyGroupsFragment()
            loadFragment(initialFragment)
        }

        // 2. Setup Navigation Listener (Switching Fragments)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_groups -> {
                    val groupsFragment = if (hasGroups) GroupsListFragment() else EmptyGroupsFragment()
                    loadFragment(groupsFragment)
                    true
                }
                R.id.nav_friends -> {
                    // Placeholder for Friends Fragment
                    Toast.makeText(this, "Friends Tab Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_account -> {
                    loadFragment(AccountFragment()) // Loads the Account Fragment
                    true
                }
                else -> false
            }
        }

        // 3. Set up the FAB click listeners
        val fabAddExpense = findViewById<FloatingActionButton>(R.id.fab_add_expense)
        fabAddExpense.setOnClickListener {
            Toast.makeText(this, "Add Expense Clicked!", Toast.LENGTH_SHORT).show()
        }

        val fabOcr = findViewById<FloatingActionButton>(R.id.fab_ocr)
        fabOcr.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            runTextRecognition(imageBitmap)
        }
    }

    private fun runTextRecognition(imageBitmap: Bitmap) {
        val image = InputImage.fromBitmap(imageBitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processTextRecognitionResult(visionText.text)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "Text recognition failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun processTextRecognitionResult(text: String) {
        // For now, just log the extracted text.
        Log.d("OCR Result", text)
        Toast.makeText(this, "Extracted Text: $text", Toast.LENGTH_LONG).show()
    }

    private fun setupPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content_frame, fragment)
            .commit()
    }
}