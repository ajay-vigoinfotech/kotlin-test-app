package com.example.replicaapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.replicaapp.R
import com.example.replicaapp.databinding.ActivityAlertDialogsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

@Suppress("LABEL_NAME_CLASH")
class AlertDialogs : AppCompatActivity() {
    private  lateinit var binding: ActivityAlertDialogsBinding
    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAlertDialogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Hello from Vigo")
                .setPositiveButton("Cancel") {dialog, _ ->
                }
                .show()
        }

        val items = arrayOf("Tata Safari", "Pagani", "Volvo", "BMW")

        binding.btn1.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose an Car")
                .setItems(items) { dialog, which ->
                    Toast.makeText(this, "You will have ${items[which]} Car in Next 5 Years", Toast.LENGTH_SHORT).show()
                }
                .setCancelable(true)
                .show()
            true
        }

        binding.btn2.setOnLongClickListener {
            val selectedItems = BooleanArray(items.size) // all false by default

            AlertDialog.Builder(this)
                .setTitle("Select Options")
                .setMultiChoiceItems(items, selectedItems) { _, which, isChecked ->
                    selectedItems[which] = isChecked
                }
                .setPositiveButton("OK") { _, _ ->
                    val selectedList = items
                        .filterIndexed { index, _ -> selectedItems[index] }
                    Toast.makeText(this, "Selected Car: ${selectedList.joinToString()}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .show()

            true
        }

        binding.btn2.setOnClickListener {
            var selectedIndex = -1 // -1 means nothing selected by default

            AlertDialog.Builder(this)
                .setTitle("Select One Option")
                .setSingleChoiceItems(items, selectedIndex) { _, which ->
                    selectedIndex = which
                }
                .setPositiveButton("OK") { _, _ ->
                    if (selectedIndex != -1) {
                        Toast.makeText(this, "Selected Car: ${items[selectedIndex]}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "No selection made", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .show()
        }

        binding.btn3.setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)

            val etName = dialogView.findViewById<EditText>(R.id.etName)
            val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)
            val etPhone = dialogView.findViewById<EditText>(R.id.etPhone)

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Custom Form")
                .setView(dialogView)
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .create()

            alertDialog.setOnShowListener {
                val submitButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                submitButton.setOnClickListener {
                    val name = etName.text.toString().trim()
                    val email = etEmail.text.toString().trim()
                    val phone = etPhone.text.toString().trim()

                    // Validate fields
                    if (name.isEmpty()) {
                        etName.error = "Name is required"
                        Toast.makeText(this, "Name is Required", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if (email.isEmpty()) {
                        etEmail.error = "Email is required"
                        Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        etEmail.error = "Enter a valid email"
                        Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if (phone.isEmpty()) {
                        etPhone.error = "Phone no is required"
                        Toast.makeText(this, "Phone no is required", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if (!phone.matches(Regex("^[0-9]{10}$"))) {
                        etPhone.error = "Enter a valid Phone no"
                        Toast.makeText(this, "Enter a valid Phone no", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    Toast.makeText(this, "Name: $name, Email: $email Phone: $phone", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            }
            alertDialog.show()
        }

        binding.btn3.setOnLongClickListener {
            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

            val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)

            val alertDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false) // Optional: make it non-cancelable by outside tap
                .create()

            dismissButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
            true
        }

        binding.btn4.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            bottomSheetDialog.setContentView(dialogView)

            // When the bottom sheet is shown, configure its behavior
            bottomSheetDialog.setOnShowListener { dialog ->
                val bottomSheet = (dialog as BottomSheetDialog)
                    .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

                bottomSheet?.let {
                    // Set max height to screen height
                    val layoutParams = it.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                    it.layoutParams = layoutParams

                    val behavior = BottomSheetBehavior.from(it)
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    behavior.isDraggable = true
//                    behavior.peekHeight = 1000
                    behavior.isFitToContents = true
                    behavior.skipCollapsed = false
                    behavior.isHideable = true
                }
            }

            // Handle dismiss button
            val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)
            dismissButton.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }
}