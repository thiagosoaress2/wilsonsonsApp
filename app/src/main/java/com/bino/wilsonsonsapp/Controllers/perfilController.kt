package com.bino.wilsonsonsapp.Controllers

import android.app.Activity
import android.widget.EditText
import android.widget.ImageView
import com.bino.wilsonsonsapp.Models.ConsultsOccupationModel
import com.bino.wilsonsonsapp.Models.ObjectUser
import com.bino.wilsonsonsapp.R
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.PhoneNumberFormatType
import com.bino.wilsonsonsapp.Utils.PhoneNumberFormatter
import com.bumptech.glide.Glide
import java.lang.ref.WeakReference

object perfilController {

    lateinit var objectsUser : ObjectUser
    lateinit var consultOcupation: ConsultsOccupationModel

    fun loadData(){
        objectsUser = ObjectUser()
        consultOcupation = ConsultsOccupationModel()
    }

    fun loadImage(activity: Activity, imageView: ImageView, hasPic: Boolean){

        if (hasPic){

            try {
                Glide.with(activity)
                    .load(objectsUser.photo)
                    .thumbnail(0.9f)
                    .skipMemoryCache(true)
                    .transform(CircleTransform(activity)) // applying the image transformer
                    .into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {

            try {
                Glide.with(activity)
                    .load(R.drawable.avatar)
                    .thumbnail(0.9f)
                    .skipMemoryCache(true)
                    .transform(CircleTransform(activity)) // applying the image transformer
                    .into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }

    fun loadCelFormater(activity: Activity, editText: EditText){
        val country = PhoneNumberFormatType.PT_BR // OR PhoneNumberFormatType.PT_BR
        val phoneFormatter = PhoneNumberFormatter(WeakReference(editText), country)
        editText.addTextChangedListener(phoneFormatter)
    }

    fun savePhoto(url: String){
        objectsUser.photo = url
    }

    fun saveNasc(data: String){
        objectsUser.datenascimento = data
    }
    fun saveName(data: String){
        objectsUser.name = data
    }
    fun saveCel(data: String){
        objectsUser.number = data
    }
    fun saveEstado(data: Int){
        objectsUser.state = data
    }
    fun saveFuncao(data: Int){
        objectsUser.cargo = data
    }

    fun getOcupation(valor: Int): String{
        consultOcupation.
    }


}