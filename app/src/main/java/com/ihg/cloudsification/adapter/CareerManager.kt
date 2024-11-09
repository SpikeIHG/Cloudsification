package com.ihg.cloudsification.adapter

import android.content.Context
import android.content.SharedPreferences


class CareerManager(context: Context) {
    val atlas_name = "Atlas_Count"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("YourCareer", Context.MODE_PRIVATE)


    fun initTotalNum(num : Long){
        sharedPreferences.edit().putLong(atlas_name,num).apply()
    }


    fun addSpecifiedGeneCloud(key: String){
        val previous_num = sharedPreferences.getLong(key,0)
        sharedPreferences.edit().putLong(key,previous_num+1).apply()
        val previous_num_all = sharedPreferences.getLong(atlas_name,0)
        sharedPreferences.edit().putLong(atlas_name,previous_num_all+1).apply()
    }


    fun declineSpecifiedGeneCloud(key: String):Boolean{
        val previous_num = sharedPreferences.getLong(key,0)
        val previous_num_all = sharedPreferences.getLong(atlas_name,0)
        if(previous_num == 0L)
            return false
        sharedPreferences.edit().putLong(key,previous_num-1).apply()
        sharedPreferences.edit().putLong(atlas_name,previous_num_all-1).apply()
        return true
    }

    fun getAllAtlasNum() : Long{
        val num_all = sharedPreferences.getLong(atlas_name,0)
        return num_all
    }


    fun removeGene(key: String){
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }




    fun getSpecifiedGeneCloudNum(key: String):Long{
        val count = sharedPreferences.getLong(key,0)
        return count
    }


    fun getAllGeneNumArray():MutableList<Pair<String ,Long>>{
        val all_entries = sharedPreferences.all
        val res = mutableListOf<Pair<String,Long>>()
        for((gene,num) in all_entries)
            if(gene != atlas_name)
            res.add(Pair(gene, num as Long))
        return res
    }

    fun getMaxNumGene(): String {
        val numArray = mutableListOf<Pair<String, Long>>()  // 存储 gene 和 num 的配对

        // 遍历 sharedPreferences 中的所有条目
        for ((gene, num) in sharedPreferences.all) {
            if (gene != atlas_name) {
                numArray.add(gene to num as Long)  // 将 gene 和 num 作为 Pair 添加到 numArray
            }
        }

        // 使用 maxByOrNull 找到 num 最大的 Pair
        val maxPair = numArray.maxByOrNull { it.second }

        // 如果 maxPair 不为空，返回对应的 gene，否则返回默认值
        if(maxPair?.second == 0L) return ""
        return maxPair?.first ?: "查不到呜呜~~"
    }


}