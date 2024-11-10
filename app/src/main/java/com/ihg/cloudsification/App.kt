package com.ihg.cloudsification



import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.database.CloudCardDatabaseHelper

import com.ihg.cloudsification.adapter.BadgeManager
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.GlideImageLoader
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.entity.CloudCard
import lv.chi.photopicker.ChiliPhotoPicker
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class App: Application() {
    private lateinit var preferencesManager: PreferencesManager
    @SuppressLint("NewApi")
    override fun onCreate() {
        super.onCreate()

        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.ihg.cloudsification.fileprovider"
        )

         preferencesManager = PreferencesManager(this)
        createAppFolder()






    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAppFolder() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)



        if (isFirstLaunch) {
            val folderName = "DouDou"
            val appFolder = File(filesDir, folderName)
            if (!appFolder.exists()) {
                appFolder.mkdir()  // 创建文件夹
            }
            sharedPreferences.edit().putLong("LifeSpan",1L).apply()
            sharedPreferences.edit().putString("LastTimeLaunch", LocalDate.now().toString()).apply()
            val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            sharedPreferences.edit().putBoolean("GPTenabled",false).apply()
            sharedPreferences.edit().putString("LastMonth", currentMonth).apply()
            sharedPreferences.edit().putString("key0","马孔多在下雨").apply()
            sharedPreferences.edit().putString("key1","19770905").apply()
            sharedPreferences.edit().putString("key2","20000616").apply()
            sharedPreferences.edit().putString("key3","blame it on the black star").apply()
            sharedPreferences.edit().putLong(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")),3).apply()


            val sharedPreferences_stat = CareerManager(this)
            val badgeManager = BadgeManager(this)
           preferencesManager.saveGeneraSpecies("棉花糖云", arrayOf("原味棉花糖云","草莓味棉花糖云"))
            preferencesManager.saveDescription("棉花糖云","   蓬松的积云就像是一朵朵漂浮的巨型棉花糖，若是傍晚时分看见，云朵会变成粉红色，那就是草莓味的棉花糖(想起了作为哈斯人的日子) ^ω^")
            preferencesManager.saveDescription("原味棉花糖云_S","晴空下的积云")
            preferencesManager.saveDescription("草莓味棉花糖云_S","傍晚时分西边天空的积云")
            // 更新标志为已启动
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
            badgeManager.initBadge()

            sharedPreferences_stat.initTotalNum(0L)
            val assetmanager = assets
            var inputStream: InputStream? = null
            try {
                
                inputStream = assetmanager?.open("images/white.jpg") // 读取 assets 文件
                val bitmap1 = BitmapFactory.decodeStream(inputStream)
                val file1 = File(appFolder,"white.jpg")
                FileOutputStream(file1).use { outputStream -> bitmap1.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                }

               /* val newDescription = dialogDescriptionEdit.text.toString()
                val newttag = spinner.selectedItem*/
                // 更新数据
                val new_card1 = CloudCard(
                    0,
                   "棉花糖云",
                    file1.toString(),
                    "2022-02-06 14:31",
                    "太阳系地球",
                    "   示例 ———— 一朵蓬松的浓积云（原味）"
                )



                inputStream = assetmanager?.open("images/pink.jpg") // 读取 assets 文件
                val bitmap2 = BitmapFactory.decodeStream(inputStream)
                val file2 = File(appFolder,"pink.jpg")
                FileOutputStream(file2).use { outputStream -> bitmap2.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                }

               
                // 更新数据
                val new_card2 = CloudCard(
                    0,
                    "棉花糖云",
                    file2.toString(),
                    "2024-08-02 19:31",
                    "太阳系地球",
                    "   示例 ———— 一朵向晚的粉色积云（草莓味）"
                )


                inputStream = assetmanager?.open("images/orange.jpg") // 读取 assets 文件
                val bitmap3 = BitmapFactory.decodeStream(inputStream)
                val file3 = File(appFolder,"orange.jpg")
                FileOutputStream(file3).use { outputStream -> bitmap3.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                }


                // 更新数据
                val new_card3 = CloudCard(
                    0,
                    "棉花糖云",
                    file3.toString(),
                    "2020-06-20 18:31",
                    "太阳系地球",
                    "   示例 ———— 一朵向晚的传说积云（柠檬味）创建你的第一张卡片吧"
                )

                val dbhelper =  CloudCardDatabaseHelper(this,"DouDou",1)
                new_card1.id = dbhelper.addItem(new_card1)
                new_card2.id = dbhelper.addItem(new_card2)
                new_card3.id = dbhelper.addItem(new_card3)

                sharedPreferences_stat.addSpecifiedGeneCloud("棉花糖云")
                sharedPreferences_stat.addSpecifiedGeneCloud("棉花糖云")
                sharedPreferences_stat.addSpecifiedGeneCloud("棉花糖云")

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close() // 关闭输入
            }

        }
    }
}