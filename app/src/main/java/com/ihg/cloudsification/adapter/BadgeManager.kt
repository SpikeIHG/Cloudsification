package com.ihg.cloudsification.adapter

import android.content.Context
import android.content.SharedPreferences

class BadgeManager(context: Context) {
    val badge_num = "badge_num"
    val badge_locked_num = "badge_locked_num"
    val badge_unlocked_num = "badge_unlocked_num"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("YourBadge", Context.MODE_PRIVATE)
   val keys = arrayOf(
        "吃掉一朵棉花糖",
        "白色波浪",
        "雾中风景",
        "踢球的最好时光",
        "暴雨将至",
        "破碎白云之心",
        "阴天",
        "白色精灵",
        "白丝绒",
        "天空结了霜",
        "云彩收藏家",
        "我的天空里"
    )
    /*  val desc = arrayOf(
         "发现一朵积云",
         "发现一朵层积云",
         "发现一朵层云",
         "发现一朵雨层云",
         "发现一朵积雨云",
         "发现一朵高积云",
         "发现一朵高层云",
         "发现一朵卷云",
         "发现一朵卷层云",
         "发现一朵卷积云",
         "集齐十大云属",
         "自定义一种云"
     )
 
      val numArray = mutableListOf<Pair<String, String>>(
          keys[0] to desc[0],
          keys[1] to desc[1],
          keys[2] to desc[2],
          keys[3] to desc[3],
          keys[4] to desc[4],
          keys[5] to desc[5],
          keys[6] to desc[6],
          keys[7] to desc[7],
          keys[8] to desc[8],
          keys[9] to desc[9],
          keys[10] to desc[10],
          keys[11] to desc[11],
 
 
      )*/

    fun getUnlockedBadges(): Array<String>{
        val badges  = mutableListOf<String>()
        keys.forEach { value -> if(!isUnLocked(value)) badges.add(value) }
        return badges.toTypedArray()
    }





    fun getLockedBadgeNum() : Long
    {
        return sharedPreferences.getLong(badge_locked_num,0)
    }

    fun getUnLockedBadgeNum() : Long
    {
        return sharedPreferences.getLong(badge_unlocked_num,0)
    }

    fun initBadge()
    {
        sharedPreferences.edit().putBoolean(keys[0],false).apply()
        sharedPreferences.edit().putBoolean(keys[1],false).apply()
        sharedPreferences.edit().putBoolean(keys[2],false).apply()
        sharedPreferences.edit().putBoolean(keys[3],false).apply()
        sharedPreferences.edit().putBoolean(keys[4],false).apply()
        sharedPreferences.edit().putBoolean(keys[5],false).apply()
        sharedPreferences.edit().putBoolean(keys[6],false).apply()
        sharedPreferences.edit().putBoolean(keys[7],false).apply()
        sharedPreferences.edit().putBoolean(keys[8],false).apply()
        sharedPreferences.edit().putBoolean(keys[9],false).apply()
        sharedPreferences.edit().putBoolean(keys[10],false).apply()
        sharedPreferences.edit().putBoolean(keys[11],false).apply()

       /* sharedPreferences.edit().putString(keys[0],desc[0]).apply()
        sharedPreferences.edit().putString(keys[1],desc[1]).apply()
        sharedPreferences.edit().putString(keys[2],desc[2]).apply()
        sharedPreferences.edit().putString(keys[3],desc[3]).apply()
        sharedPreferences.edit().putString(keys[4],desc[4]).apply()
        sharedPreferences.edit().putString(keys[5],desc[5]).apply()
        sharedPreferences.edit().putString(keys[6],desc[6]).apply()
        sharedPreferences.edit().putString(keys[7],desc[7]).apply()
        sharedPreferences.edit().putString(keys[8],desc[8]).apply()
        sharedPreferences.edit().putString(keys[9],desc[9]).apply()
        sharedPreferences.edit().putString(keys[10],desc[10]).apply()
        sharedPreferences.edit().putString(keys[11],desc[11]).apply()*/

        sharedPreferences.edit().putLong(badge_locked_num,12L).apply()
        sharedPreferences.edit().putLong(badge_unlocked_num,0L).apply()
    }

    fun unlockSpecifiedBadge(key: String){
        sharedPreferences.edit().putBoolean(key,true).apply()
        val previous_num_locked = sharedPreferences.getLong(badge_locked_num,12)
        sharedPreferences.edit().putLong(badge_locked_num,previous_num_locked-1).apply()
        sharedPreferences.edit().putLong(badge_unlocked_num,13-previous_num_locked).apply()
    }

    fun isUnLocked(key: String):Boolean{
        return sharedPreferences.getBoolean(key,false)
    }
}