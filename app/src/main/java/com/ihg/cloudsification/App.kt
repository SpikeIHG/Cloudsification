package com.ihg.cloudsification



import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.ihg.cloudsification.adapter.BadgeManager
import com.ihg.cloudsification.adapter.GlideImageLoader
import com.ihg.cloudsification.adapter.PreferencesManager
import lv.chi.photopicker.ChiliPhotoPicker
import java.io.File
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
            sharedPreferences.edit().putString("LastMonth", currentMonth).apply()

            val sharedPreferences_stat = getSharedPreferences("YourCareer", Context.MODE_PRIVATE)
            val badgeManager = BadgeManager(this)
           preferencesManager.saveGeneraSpecies("棉花糖云", arrayOf("原味棉花糖云","草莓味棉花糖云"))
            preferencesManager.saveDescription("棉花糖云","蓬松的积云就像是一朵朵漂浮的巨型棉花糖，若是傍晚时分看见，云朵会变成粉红色，那就是草莓味的棉花糖(想起了作为哈斯人的日子) ^ω^")
            preferencesManager.saveDescription("原味棉花糖云  _S","晴空下的积云")
            preferencesManager.saveDescription("草莓味味棉花糖云  _S","傍晚时分西边天空的积云")
            // 更新标志为已启动
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
            sharedPreferences_stat.edit().putLong("Atlas_Count",0).apply()             //
            badgeManager.initBadge()

        }
    }
}