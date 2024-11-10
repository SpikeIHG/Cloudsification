package com.ihg.cloudsification

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/*
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!isTaskRoot) {
            Log.d("S","!!!!!!!!!")
            finish()
            return
        }
        // 延迟跳转到主界面
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
//
//            startActivity(Intent(this, MainActivity::class.java))
            //finish() // 结束启动界面 Activity
            overridePendingTransition(0, R.anim.slide_top)
        }, 3000) // 启动界面显示时间，单位为毫秒
    }

    override fun onPause() {
        super.onPause()
        Log.d("K",")))))))))")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("S",")))))))))0000000")
    }
}
*/
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        // 使用 Handler 进行延迟跳转
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, R.anim.slide_top)
        }, 3000) // 设置启动界面显示时间
    }
}