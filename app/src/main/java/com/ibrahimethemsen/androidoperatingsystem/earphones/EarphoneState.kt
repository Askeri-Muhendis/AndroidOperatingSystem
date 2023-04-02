package com.ibrahimethemsen.androidoperatingsystem.earphones

sealed class EarphoneState {
        object Earphone : EarphoneState()
        object NotEarphone : EarphoneState()
        object MicrophoneEarphone : EarphoneState()
}