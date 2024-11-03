package com.ihg.cloudsification

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Created by Depression on 12-08-2018.
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun hideKeyboardFrom(context: Context?, view: View) {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
}


fun convertToDMS(coordinate: Double): String {
    val degrees = coordinate.toInt()
    val minutes = ((coordinate - degrees) * 60).toInt()
    val seconds = ((coordinate - degrees - minutes / 60.0) * 3600).toInt()

    return String.format("%d° %d' %d\"", degrees, minutes, seconds)
}

// 示例：将经纬度转换为 DMS 格式
fun displayCoordinates(latitude: Double, longitude: Double) {
    val dmsLatitude = convertToDMS(latitude)
    val dmsLongitude = convertToDMS(longitude)

    /*   println("纬度: $dmsLatitude")
       println("经度: $dmsLongitude")*/
    Log.d("XXXX","纬度: $dmsLatitude")
    Log.d("XXXX","经度: $dmsLongitude")
}
