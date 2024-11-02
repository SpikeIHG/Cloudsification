package com.ihg.cloudsification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.fridayof1995.tabanimation.SnapTabLayout
//import kotlinx.android.synthetic.main.activity_launcher.*


class LauncherActivity : AppCompatActivity() {

    private lateinit var spinner_num_tabs: Spinner
    private lateinit var btGo: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        spinner_num_tabs  = findViewById<Spinner>(R.id.spinner_num_tabs);
        btGo= findViewById<Button>(R.id.btGo);

        val numOfTabs = ArrayList<Int>()//Creating an empty arraylist
        numOfTabs.add(SnapTabLayout.NumOfTabs.THREE.value)//Adding object in arraylist
        numOfTabs.add(SnapTabLayout.NumOfTabs.FIVE.value)
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, numOfTabs)

        spinner_num_tabs.adapter = adapter

        btGo.setOnClickListener {
            val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            intent.putExtra("numOfTabs", spinner_num_tabs.selectedItem.toString().toInt())
            startActivity(intent)
        }
    }
}
