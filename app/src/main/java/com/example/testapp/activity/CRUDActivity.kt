package com.example.testapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.adapter.EntryAdapter
import com.example.testapp.base.BaseActivity
import com.example.testapp.databinding.ActivityCrudBinding
import com.example.testapp.model.Entry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CRUDActivity : BaseActivity() {
    private lateinit var binding: ActivityCrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.crudToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.crudToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val sharedPrefs = getSharedPreferences("crud_data", Context.MODE_PRIVATE)
        val jsonList = sharedPrefs.getString("entries", null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Entry>>() {}.type

        val list: MutableList<Entry> = if (jsonList != null) {
            gson.fromJson(jsonList, type) as MutableList<Entry>
        } else {
            mutableListOf()
        }

        val adapter = EntryAdapter(
            list,
            onEditClick = { entry, position ->
                val intent = Intent(this, CreateActivity::class.java)
                intent.putExtra("name", entry.name)
                intent.putExtra("amount", entry.amount)
                intent.putExtra("type", entry.type)
                intent.putExtra("index", position) // Pass index for update logic
                intent.putStringArrayListExtra("selectedItems", ArrayList(entry.selectedItems))
                startActivity(intent)
            },
            onDeleteClick = { entry ->
                AlertDialog.Builder(this)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, _ ->
                        val updatedList = list.toMutableList().apply { remove(entry) }
                        val updatedJson = Gson().toJson(updatedList)
                        sharedPrefs.edit().putString("entries", updatedJson).apply()
                        loadData() // Reload after deletion
                        dialog.dismiss()
                        Toast.makeText(this, "Card Deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}