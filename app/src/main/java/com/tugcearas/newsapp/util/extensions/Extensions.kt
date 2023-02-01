package com.tugcearas.newsapp.util.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toastMessage(msg:String) = Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

