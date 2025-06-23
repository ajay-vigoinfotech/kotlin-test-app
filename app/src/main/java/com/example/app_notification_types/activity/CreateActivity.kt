package com.example.app_notification_types.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_notification_types.R
import com.example.app_notification_types.base.BaseActivity
import com.example.app_notification_types.databinding.ActivityCreateBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class CreateActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateBinding
    private var editingIndex: Int? = null

    data class Entry(
        val name: String,
        val amount: String,
        val type: String,
        val selectedItems: List<String>
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.crudToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.crudToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Check for edit mode
        val entryName = intent.getStringExtra("name")
        val entryAmount = intent.getStringExtra("amount")
        val entryType = intent.getStringExtra("type")
        editingIndex = intent.getIntExtra("index", -1).takeIf { it != -1 }

        if (entryName != null && entryAmount != null && entryType != null) {
            binding.nameEditText.setText(entryName)
            binding.edit1.setText(entryAmount)

            // Set selected radio button
            when (entryType) {
                "Income" -> binding.radioIncome.isChecked = true
                "Expense" -> binding.radioExpense.isChecked = true
            }

            // Set selected checkboxes
            val selectedItemsList = intent.getStringArrayListExtra("selectedItems")
            if (selectedItemsList != null) {
                binding.c1.isChecked = selectedItemsList.contains(binding.c1.text.toString())
                binding.c2.isChecked = selectedItemsList.contains(binding.c2.text.toString())
                binding.c3.isChecked = selectedItemsList.contains(binding.c3.text.toString())
                binding.c4.isChecked = selectedItemsList.contains(binding.c4.text.toString())
            }

            binding.btn1.text = "Update"
            binding.crudToolbar.title = "Update"
        }

        binding.btn1.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val amount = binding.edit1.text.toString().trim()

            val type = when (binding.radioGroupType.checkedRadioButtonId) {
                R.id.radioIncome -> "Income"
                R.id.radioExpense -> "Expense"
                else -> ""
            }

            val selectedItems = mutableListOf<String>()

            if (binding.c1.isChecked) selectedItems.add(binding.c1.text.toString())
            if (binding.c2.isChecked) selectedItems.add(binding.c2.text.toString())
            if (binding.c3.isChecked) selectedItems.add(binding.c3.text.toString())
            if (binding.c4.isChecked) selectedItems.add(binding.c4.text.toString())

            if (name.isNotEmpty() && amount.isNotEmpty() && type.isNotEmpty()) {
                val sharedPrefs = getSharedPreferences("crud_data", Context.MODE_PRIVATE)
                val gson = Gson()
                val jsonList = sharedPrefs.getString("entries", null)
                val typeToken = object : TypeToken<MutableList<Entry>>() {}.type
                val list: MutableList<Entry> =
                    if (jsonList != null) gson.fromJson(jsonList, typeToken) else mutableListOf()

                if (editingIndex != null) {
                    val oldEntry = list[editingIndex!!]
                    if (oldEntry.name == name && oldEntry.amount == amount && oldEntry.type == type && oldEntry.selectedItems == selectedItems) {
                        Toast.makeText(this, "No changes detected.", Toast.LENGTH_SHORT).show()
                    } else {
                        list[editingIndex!!] = Entry(name, amount, type, selectedItems)
                        val updatedJson = gson.toJson(list)
                        sharedPrefs.edit().putString("entries", updatedJson).apply()
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    list.add(Entry(name, amount, type, selectedItems))
                    val updatedJson = gson.toJson(list)
                    sharedPrefs.edit().putString("entries", updatedJson).apply()
                    Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}