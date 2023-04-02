package com.ibrahimethemsen.androidoperatingsystem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimethemsen.androidoperatingsystem.battery.BatteryStatusTracker
import com.ibrahimethemsen.androidoperatingsystem.earphones.EarphoneState
import com.ibrahimethemsen.androidoperatingsystem.earphones.EarphonesStatusTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val batteryStatusTracker: BatteryStatusTracker,
    private val earphonesStatusTracker: EarphonesStatusTracker
) : ViewModel() {
    private val _batteryStatus = MutableLiveData<Boolean>()
    val batteryStatus : LiveData<Boolean> = _batteryStatus

    private val _earphoneStatus = MutableLiveData<EarphoneState>()
    val earphoneStatus : LiveData<EarphoneState> = _earphoneStatus

    init {
        batteryStatusFlow()
        earphonesStatusFlow()
    }

    private fun earphonesStatusFlow(){
        earphonesStatusTracker.observeHeadsetConnection().flowToLiveData(_earphoneStatus)
    }

    private fun batteryStatusFlow(){
        batteryStatusTracker.observeBattery().flowToLiveData(_batteryStatus)
    }

    private fun <T> Flow<T>.flowToLiveData(liveData: MutableLiveData<T>) {
        viewModelScope.launch {
            collect {
                liveData.postValue(it)
            }
        }
    }
}