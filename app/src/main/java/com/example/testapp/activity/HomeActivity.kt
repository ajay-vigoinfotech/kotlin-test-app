package com.example.testapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.testapp.base.BaseActivity
import com.example.testapp.databinding.ActivityHomeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.materialSwitch.isChecked = isDarkTheme
        binding.materialSwitch.setOnClickListener {
            toggleThemeAndRecreate()
        }

        binding.btn1.setOnClickListener {
            showProgressAndNavigate {
                startActivity(Intent(this, CRUDActivity::class.java))
            }
        }

        binding.btn3.setOnClickListener {
            showProgressAndNavigate {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        binding.btn4.setOnClickListener {
            showProgressAndNavigate {
                startActivity(Intent(this, Task1::class.java))
            }
        }

        binding.btn5.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val currentDateTime = LocalDateTime.now()
                val dateTime = getCurrentDateTime()
                binding.tv1.text = dateTime

                val formats: List<String> = listOf(
                    " ${ currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}",
                    " ${currentDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                    " ${currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))}",
                    " ${currentDateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))}",
                    " ${currentDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}",
                )

                val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_list_item_1, formats)
                binding.userList.adapter = adapter
            } else {
                binding.tv1.text = "Requires Android 8.0+ (API 26)"
            }
        }

        binding.btn6.setOnLongClickListener(){
            val intent = Intent(this, AlertDialogs::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Long Pressed", Toast.LENGTH_LONG).show()
            true
        }

        binding.btn6.setOnClickListener(){
            val intent = Intent(this, AlertDialogs::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btn7.setOnClickListener(){
            val intent = Intent(this, AppNotification::class.java)
            startActivity(intent)
//            Toast.makeText(this, "Notification received", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    private fun showProgressAndNavigate(navigate: () -> Unit) {
        binding.progressContainer.visibility = View.VISIBLE
        binding.btn1.isEnabled = false
        binding.btn3.isEnabled = false
        binding.btn4.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            navigate()
            binding.progressContainer.visibility = View.GONE
            binding.btn1.isEnabled = true
            binding.btn3.isEnabled = true
            binding.btn4.isEnabled = true
        }, 500)
    }
}