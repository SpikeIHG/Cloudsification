package com.ihg.cloudsification



import android.app.Application
import android.content.Context
import com.ihg.cloudsification.adapter.GlideImageLoader
import lv.chi.photopicker.ChiliPhotoPicker
import java.io.File

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ChiliPhotoPicker.init(
            loader = GlideImageLoader(),
            authority = "com.ihg.cloudsification.fileprovider"
        )
    }

    private fun createAppFolder() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            val folderName = "DouDou"
            val appFolder = File(filesDir, folderName)
            if (!appFolder.exists()) {
                appFolder.mkdir()  // 创建文件夹
            }

            // 更新标志为已启动
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }
    }
}