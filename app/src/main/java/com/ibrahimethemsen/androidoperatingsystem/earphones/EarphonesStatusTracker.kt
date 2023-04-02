package com.ibrahimethemsen.androidoperatingsystem.earphones

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EarphonesStatusTracker(private val context: Context) {
    fun observeHeadsetConnection(): Flow<EarphoneState> = callbackFlow {
        val headsetReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_HEADSET_PLUG) {
                    val state = intent.getIntExtra("state", -1) //HEADSET_STATE_PLUGGED-HEADSET_STATE_UNPLUGGED
                    val hasMicrophone = intent.getIntExtra("microphone", -1) //DEVICE_OUT_WIRED_HEADSET_MIC-DEVICE_OUT_WIRED_HEADSET
                    if (state == 1 && hasMicrophone == 1) {
                        trySend(EarphoneState.MicrophoneEarphone) // Kulaklık
                    } else if (state == 1) {
                        trySend(EarphoneState.Earphone) // Kulaklık + Mikrofon
                    }else{
                        // Kulaklık takılı değil
                        trySend(EarphoneState.NotEarphone)
                    }
                }
            }
        }
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        context.registerReceiver(headsetReceiver, filter)

        awaitClose { context.unregisterReceiver(headsetReceiver) }
    }
}