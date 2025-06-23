package com.example.app_notification_types.activity

import android.content.Intent
import android.os.Bundle
import com.example.app_notification_types.base.BaseActivity
import com.example.app_notification_types.databinding.ActivityTask1Binding

class Task1 : BaseActivity() {
    private lateinit var binding: ActivityTask1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTask1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra("access_token")
        binding.editText1.setText(accessToken)

        val list1 = arrayListOf<String>()
        val list2 = arrayListOf<String>()
        val list3 = arrayListOf<String>()

        binding.elevatedButton.setOnClickListener {
            val text1 = binding.editText1.text.toString().trim()
            val text2 = binding.editText2.text.toString().trim()
            val text3 = binding.editText3.text.toString().trim()

            if (text1.isNotEmpty()) list1.add(text1)
            if (text2.isNotEmpty()) list2.add(text2)
            if (text3.isNotEmpty()) list3.add(text3)

            val intent = Intent(this, MaterialDesign::class.java).apply {
                putStringArrayListExtra("text1", list1)
                putStringArrayListExtra("text2", list2)
                putStringArrayListExtra("text3", list3)

                binding.editText1.text?.clear()
                binding.editText2.text?.clear()
                binding.editText3.text?.clear()
            }
            startActivity(intent)
        }
    }
}