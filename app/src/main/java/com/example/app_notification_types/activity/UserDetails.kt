package com.example.app_notification_types.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.app_notification_types.databinding.ActivityUserDetailsBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

class UserDetails : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName = getDeviceName()
        val androidVersion = getAndroidVersion()
        val sdkVersion = getSdkVersion()
        val battery = getBatteryPercentage(this)

        binding.tv1.text = "Device name : $deviceName\n Android version : $androidVersion\n SDK Version : $sdkVersion\n Battery : $battery%"

        binding.btn1.setOnClickListener {
            requestLocation(this)
        }

        binding.btn1.setOnLongClickListener {
            if (isInternetAvailable(this)) {
                AlertDialog.Builder(this)
                    .setTitle("Internet is connected")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                Toast.makeText(this, "Internet is connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
            }
            true  // Return true to indicate the long-click was handled
        }

        binding.btn2.setOnClickListener {
            if (!isAutoDateTimeEnabled()) {
                AlertDialog.Builder(this@UserDetails)
                    .setTitle("Manual Time Detected")
                    .setMessage("Your device date/time is set manually. Please enable automatic date & time.")
                    .setCancelable(true)
                    .setPositiveButton("Open Settings") { dialog, _ ->
                        dialog.dismiss()
                        val intent = Intent(android.provider.Settings.ACTION_DATE_SETTINGS)
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                Toast.makeText(this, "Automatic date & time is enabled", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //Check Internet
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun requestLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            return
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                val lat = location.latitude
                val lng = location.longitude
                val speed = location.speed            // meters/second
                val accuracy = location.accuracy      // meters

                Log.d("LocationData", "Lat: $lat, Lng: $lng, Speed: $speed m/s, Accuracy: $accuracy m")
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun getDeviceName() : String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return  if (model.startsWith(manufacturer)) {
            model.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        } else {
            "${manufacturer.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} $model"
        }
    }

    fun getAndroidVersion(): String {
        return Build.VERSION.RELEASE
    }

    fun getSdkVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    private fun getBatteryPercentage(context: Context): Int {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        return if (level >= 0 && scale > 0) {
            (level * 100) / scale
        } else {
            -1
        }
    }

    private fun isAutoDateTimeEnabled(): Boolean {
        return try {
            android.provider.Settings.Global.getInt(contentResolver, android.provider.Settings.Global.AUTO_TIME) == 1
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
