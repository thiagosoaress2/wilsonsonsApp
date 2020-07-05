package com.bino.wilsonsonsapp.Utils

import android.app.Activity
import android.media.MediaPlayer

    fun startSound(activity: Activity?, sound: Int) {
        val Sound: MediaPlayer = MediaPlayer.create(activity, sound)
        Sound.start()

}


