package com.example.replicaapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.replicaapp.R
import com.example.replicaapp.base.BaseActivity
import com.example.replicaapp.databinding.ActivityLoginBinding
import com.example.replicaapp.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : BaseActivity() {
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var binding :ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressContainer = findViewById<LinearLayout>(R.id.progressContainer)
        val grantInput = findViewById<TextInputEditText>(R.id.editText1)
        val usernameInput = findViewById<TextInputEditText>(R.id.editText2)
        val passwordInput = findViewById<TextInputEditText>(R.id.editText3)
        val submitButton = findViewById<Button>(R.id.elevatedButton)

        submitButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val grant = grantInput.text.toString().trim()
//            val grantType = "password"

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(grant, username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.tokenResponse.observe(this) { token ->
            val intent = Intent(this, Task1::class.java).apply {
                putExtra("access_token", token.access_token)
            }
            startActivity(intent)
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) { isLoading ->
            progressContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}