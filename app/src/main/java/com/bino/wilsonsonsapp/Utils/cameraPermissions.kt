package com.bino.wilsonsonsapp.Utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object cameraPermissions {

    fun hasPermissions(activity : Activity): Boolean{
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
    }

    fun checkPermission (activity : Activity, code: Int){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

        } else {
            requestPermission(activity, code)
        }

    }

    fun requestPermission(activity: Activity, code: Int){
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA), code)
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, code: Int) {
        when(requestCode){

            code -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                    Log.i("teste","Agreed permission")
                else
                    Log.i("teste","Not agreed permission")
            }
        }
    }


}
