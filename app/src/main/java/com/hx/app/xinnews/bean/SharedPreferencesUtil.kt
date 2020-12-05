package com.hx.app.xinnews.bean

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class  SharedPreferencesUtil<T>(context:Context,val name:String, val defaultValue:T): ReadWriteProperty<Any?, T> {
    private val preferences by lazy{
        context.applicationContext.getSharedPreferences("xin_news",Context.MODE_PRIVATE)
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
           putValue(name,value)
    }

    private fun putValue(name: String, value: T) = with(preferences.edit()){
        when(value){
            is Long ->putLong(name,value)
            is Float -> putFloat(name,value)
            is Int -> putInt(name,value)
            is String -> putString(name,value)
            is Boolean -> putBoolean(name,value)
            else -> throw IllegalArgumentException("The type can not be saved!")
        }.apply()

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findValue(name,defaultValue)
    }

    private fun findValue(name: String, defaultValue: T): T = with(preferences) {
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