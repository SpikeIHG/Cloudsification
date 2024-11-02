package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ihg.cloudsification.entity.CloudCard


class CloudCardDatabaseHelper(context: Context, DATABASE_NAME: String?, DATABASE_VERSION: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // 创建表格
    private val tableName = "MsWinter"

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + tableName + " ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " tag TEXT NOT NULL," +
                " imageUri TEXT NOT NULL," +
                " time TEXT NOT NULL,"+
                " location TEXT NOT NULL,"+
                " description TEXT);"
        db.execSQL(createTable)
    }

    // 升级表格
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    // 增加条目
    fun addItem(item: CloudCard): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("imageUri", item.imageUri)

            put("tag", item.tag)
            put("location", item.location)
            put("time", item.time)
            put("description", item.description)

        }
        val new_id = db.insert(tableName, null, values)
        db.close()
        Log.d("data","add one !!!!!!!!!")
        return new_id
    }

    // 删除条目
    fun deleteItem(id: Long) {
        val db = writableDatabase
        db.delete(tableName, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // 更新条目
    fun updateItem(item: CloudCard) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("imageUri", item.imageUri)
            put("description", item.description)
        }
        db.update(tableName, values, "id = ?", arrayOf(item.id.toString()))
        db.close()
    }

    // 查询所有条目
    fun getAllItems(): MutableList<CloudCard> {
        val items = mutableListOf<CloudCard>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val _id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val _imageUri = cursor.getString(cursor.getColumnIndexOrThrow("imageUri"))
                val _tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"))
                val _description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val _location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                val _time = cursor.getString(cursor.getColumnIndexOrThrow("time"))

                items.add(CloudCard(_id,_tag, _imageUri,_time,_location, _description))
            } while (cursor.moveToNext())
        }

       /* if (cursor != null && cursor.moveToFirst()) {
            val imageUriColumnIndex = cursor.getColumnIndex("imageUri")
            val descriptionColumnIndex = cursor.getColumnIndex("description")

            if (imageUriColumnIndex != -1 && descriptionColumnIndex != -1) {
                do {
                    val _id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                    val _imageUri = cursor.getString(cursor.getColumnIndexOrThrow("imageUri"))
                    val _tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"))
                    val _description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                    val _location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                    val _time = cursor.getString(cursor.getColumnIndexOrThrow("time"))

                    items.add(CloudCard(_id,_tag, _imageUri,_time,_location, _description))
                } while (cursor.moveToNext())
            } else {
                // 处理列名不存在的情况
                Log.e("DatabaseError", "Column name not found")
            }
        }*/



        cursor.close()
        db.close()
        return items
    }
}
