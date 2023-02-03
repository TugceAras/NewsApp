package com.tugcearas.newsapp.util.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Context.toastMessage(msg:String) = Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
