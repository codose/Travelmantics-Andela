package com.codose.travelmantics_andela.utils

import android.content.Context
import android.widget.Toast

object Utils {
    fun String.showToast(context: Context){
        Toast.makeText(context, this,Toast.LENGTH_LONG).show()
    }
}