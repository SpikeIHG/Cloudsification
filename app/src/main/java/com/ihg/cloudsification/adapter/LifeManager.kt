package com.ihg.cloudsification.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class LifeManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaydiff(time_now :LocalDate): Pair<Boolean,Long>{

        val time_previous = sharedPreferences.getString("LastTimeLaunch","Error")
      if (time_previous.equals("Error"))
            return  Pair<Boolean,Long>(false,-1)
        else {
            val time_before = LocalDate.parse(time_previous)
          val diff = ChronoUnit.DAYS.between(time_before,time_now)
          var daydiff : Long = 0
          if(diff != 0L) {
              daydiff = diff + sharedPreferences.getLong("LifeSpan", 1)
              sharedPreferences.edit().putLong("LifeSpan",daydiff).apply()
              sharedPreferences.edit().putString("LastTimeLaunch",time_now.toString()).apply()
              return Pair<Boolean,Long>(true,daydiff)
          }
            else
          return Pair<Boolean,Long>(false,sharedPreferences.getLong("LifeSpan", 1))
      }
    }

    @SuppressLint("NewApi")
    fun Monthdiff(time : LocalDate): Long{
        val month_now = time.format(DateTimeFormatter.ofPattern("yyyy-MM"))
       /* val startDate =LocalDate.parse(sharedPreferences.getString("LastMonth","2024-11"))
        val endDate = LocalDate.now()  // 当前日期

// 计算两个日期之间的Period
        val period = Period.between(startDate, endDate)

// 计算相差的总月数
       return  period.years * 12 + period.months.toLong()*/

        return sharedPreferences.getLong(month_now,0)

    }

    @SuppressLint("NewApi")
    fun putCountMonth(time : LocalDate,Count :Long){
        sharedPreferences.edit().putLong(time.format(DateTimeFormatter.ofPattern("yyyy-MM")),Count).apply()
    }



    fun GPTenable(): Boolean{
        return sharedPreferences.getBoolean("GPTenabled",false)
    }


    fun setGPTenable(){
        sharedPreferences.edit().putBoolean("GPTenabled",true).apply()
    }


    fun keymatch(key:String):Boolean{
        val keys = mutableListOf<String>()
        keys.add(sharedPreferences.getString("key0","").toString())
        keys.add(sharedPreferences.getString("key1","").toString())
        keys.add(sharedPreferences.getString("key2","").toString())
        keys.add(sharedPreferences.getString("key3","").toString())

        var ismatched : Boolean = false
        keys.forEach{
            valeu -> if(key.equals(valeu))  ismatched =true
        }

        return ismatched
    }




    }
