package com.ibrahimethemsen.androidoperatingsystem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibrahimethemsen.androidoperatingsystem.battery.BatteryStatusTracker
import com.ibrahimethemsen.androidoperatingsystem.earphones.EarphonesStatusTracker

class DeviceViewModelProvider(private val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val batteryStatusTracker = BatteryStatusTracker(context)
        val earphonesStatusTracker = EarphonesStatusTracker(context)
        return MainViewModel(batteryStatusTracker, earphonesStatusTracker) as T
    }
}