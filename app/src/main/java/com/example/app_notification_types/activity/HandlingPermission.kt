package com.example.app_notification_types.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.app_notification_types.databinding.ActivityHandlingPermissionBinding

class HandlingPermission : AppCompatActivity() {
    private lateinit var binding: ActivityHandlingPermissionBinding

    // To track which button was clicked
    private var isBtn1Clicked = true

    // Register the Activity Result API for camera intent
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                if (isBtn1Clicked) {
                    binding.img1.setImageBitmap(imageBitmap)
                } else {
                    binding.img2.setImageBitmap(imageBitmap)
                }
                Toast.makeText(this, "Photo captured successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No image captured", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Cancelled or failed to capture photo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlingPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            isBtn1Clicked = true
            capturePhoto()
            Toast.makeText(this, "Opening camera...", Toast.LENGTH_SHORT).show()
        }

        binding.btn2.setOnClickListener {
            isBtn1Clicked = false
            capturePhoto()
            Toast.makeText(this, "Opening camera...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

}