package com.ibrahimethemsen.androidoperatingsystem

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ibrahimethemsen.androidoperatingsystem.databinding.ActivityMainBinding
import com.ibrahimethemsen.androidoperatingsystem.earphones.EarphoneState

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, DeviceViewModelProvider(this))[MainViewModel::class.java]
    }
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
    }

    private fun observe(){
        viewModel.apply {
            batteryStatus.observe(this@MainActivity,::batteryStateUi)
            earphoneStatus.observe(this@MainActivity,::earphonesStateUi)
        }
    }


    private fun batteryStateUi(isCharging: Boolean) {
        if (isCharging) {
            setState(binding.batteryIv, R.drawable.ic_battery,binding.batteryStatusTv,"Şarj oluyor")
        } else {
            setState(binding.batteryIv,R.drawable.ic_not_battery,binding.batteryStatusTv,"Şarj olmuyor")
        }
    }

    private fun earphonesStateUi(earphonesState: EarphoneState) {
        when(earphonesState){
            EarphoneState.Earphone -> {
                setState(binding.earphonesIv,R.drawable.ic_headphones,binding.earphonesStatusTv,"Kulaklık Takılı")
            }
            EarphoneState.MicrophoneEarphone -> {
                setState(binding.earphonesIv,R.drawable.ic_headset_mic,binding.earphonesStatusTv,"Kulaklık-Mikrofon Takılı")
            }
            EarphoneState.NotEarphone -> {
                setState(binding.earphonesIv,R.drawable.ic_headset_off,binding.earphonesStatusTv,"Kulaklık Takılı değil")
            }
        }
    }

    private fun setState(statusImage : ImageView,@DrawableRes statusImageDrawable : Int ,statusText : TextView,statusTextValue : String){
        binding.apply {
            statusImage.setImageDrawable(ContextCompat.getDrawable(this@MainActivity,statusImageDrawable))
            statusText.text = statusTextValue
        }
    }
}