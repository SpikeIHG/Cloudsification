package com.ihg.cloudsification

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 延迟跳转到主界面
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // 结束启动界面 Activity
            overridePendingTransition(0, R.anim.slide_top)
        }, 3000) // 启动界面显示时间，单位为毫秒
    }
}
