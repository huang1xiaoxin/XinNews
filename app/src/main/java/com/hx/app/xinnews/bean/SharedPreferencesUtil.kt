package com.hx.app.xinnews.bean

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class  SharedPreferencesUtil (val context:Context){
    private val preferences:SharedPreferences = context.applicationContext.getSharedPreferences("News",Context.MODE_PRIVATE)

     fun <T> putValue(name: String, value: T) = with(preferences.edit()){
        when(value){
            is Long ->putLong(name,value)
            is Float -> putFloat(name,value)
            is Int -> putInt(name,value)
            is String -> putString(name,value)
            is Boolean -> putBoolean(name,value)
            else -> throw IllegalArgumentException("The type can not be saved!")
        }.apply()

    }


     fun <T> findValue(name: String, defaultValue: T): T = with(preferences) {
       val result= when(defaultValue){
            is Long ->getLong(name,defaultValue)
            is Float -> getFloat(name,defaultValue)
            is Int -> getInt(name,defaultValue)
            is String -> getString(name,defaultValue)
            is Boolean -> getBoolean(name,defaultValue)
            else -> throw IllegalArgumentException("The type can not be saved!")
        }
        return (result as T)
    }

}


