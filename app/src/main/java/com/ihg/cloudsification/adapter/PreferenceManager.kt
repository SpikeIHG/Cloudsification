package com.ihg.cloudsification.adapter

import android.content.Context
import android.content.SharedPreferences


class PreferencesManager(context: Context) {
    private  val suffix = "_Desc"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("CustomCloud_Preferences", Context.MODE_PRIVATE)

    fun saveGeneraSpecies(key: String, value: Array<String>) {
        // 使用逗号作为分隔符将数组转换为字符串
        val combinedString = value.joinToString(separator = ",")
        with(sharedPreferences.edit()) {
            putString(key, combinedString)
            apply()
        }
    }

    fun saveDescription(key : String, value : String){
        val new_key = key + suffix
        with(sharedPreferences.edit()) {
            putString(new_key, value)
            apply()
        }
    }



    fun getSpecies(key: String): Array<String>? {
        val combinedString = sharedPreferences.getString(key, null)
        if(combinedString == "")return arrayOf()
        return combinedString?.split(",")?.toTypedArray()
    }

    fun getDescription(key: String): String?{
        return sharedPreferences.getString(key+suffix,null)
    }

    fun removeGeneraFromPreferences(key: String) {
        with(sharedPreferences.edit()) {
            val sp = getSpecies(key)
            if(sp?.size != 0)
            sp?.forEach { value -> remove(value+"_S"+suffix) }
            remove(key)
            remove(key+suffix)
            apply()
        }
    }

    fun getAllValues(): Map<String, Array<String>?> {
        val allEntries = sharedPreferences.all
        val result = mutableMapOf<String, Array<String>?>()
        for ((key, value) in allEntries) {
            val array : Array<String>? = (value as? String)?.split(",")?.toTypedArray()
            result[key] = array // 确保只获取字符串值
        }

        return result
    }

    fun getAllGenera(): Array<String>{
        val allkey = sharedPreferences.all
        val result = mutableListOf<String>()
        for((key,value) in allkey) {
            if(!key.endsWith("_Desc"))
                result.add(key)
        }
        return result.toTypedArray()
    }

    fun getTotalNum(): Long{            // 既包括云属 又包括 云种
        var result : Long = 0L
        val all_e = sharedPreferences.all
        for((key,value) in all_e)
            if(!key.endsWith("_Desc")) {
                if(getSpecies(key)!=null)
                    result = getSpecies(key)?.size!! + result + 1
            }
        return result
    }
}