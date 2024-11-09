package com.ihg.cloudsification.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.CloudCardDatabaseHelper
import com.google.android.material.button.MaterialButtonToggleGroup
import com.ihg.cloudsification.adapter.CloudCardAdapter
import com.ihg.cloudsification.FragmentArgumentDelegate
import com.ihg.cloudsification.MainActivity
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.LifeManager
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.convertToDMS
import com.ihg.cloudsification.entity.CloudCard
import com.ihg.cloudsification.fragments.wikifragments.WikiCustomFragment
import lv.chi.photopicker.PhotoPickerFragment
import org.json.JSONArray
import org.json.JSONObject
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull







private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class GalleryFragment : Fragment() , PhotoPickerFragment.Callback{

    private lateinit var dialoglocationEdit : AutoCompleteTextView
    private var param1: String? = null
    private var param2: String? = null

    private var fragmentNumber by FragmentArgumentDelegate<Number>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CloudCardAdapter
    private lateinit var dbHelper: CloudCardDatabaseHelper
    private val model_name = "model.tflite"
    private lateinit var locationManager: LocationManager
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var mycareermanager : CareerManager
    lateinit var preferencesManager: PreferencesManager
    private lateinit var sharedViewModelsub: SharedViewModel
    private  lateinit var lifeManager: LifeManager

    lateinit var adapter_sp: ArrayAdapter<String>



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            dbHelper = context.getDatabaseHelper()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mycareermanager = context?.let  { CareerManager(it) }!!
        lifeManager = LifeManager(requireContext())
        sharedViewModelsub = ViewModelProvider(requireActivity()).get(com.ihg.cloudsification.adapter.SharedViewModel::class.java)
        val btn_create_card = view.findViewById<ImageButton>(R.id.imageButton)
        val btn_info_gallery = view.findViewById<ImageButton>(R.id.infoButton)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 初始化适配器
         preferencesManager = context?.let { PreferencesManager(it) }!!
        adapter = preferencesManager?.let { CloudCardAdapter(dbHelper.getAllItems(),dbHelper, it,mycareermanager,sharedViewModelsub) }!!
        recyclerView.adapter = adapter

        btn_info_gallery.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_showinfo_gallery, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

          /*  dialog?.setOnShowListener { dialogInterface: DialogInterface? ->
                // 设置 dimAmount 为 0.8，暗度更高
                val window = dialog.window
                if (window != null) {
                    val layoutParams = window.attributes
                    layoutParams.dimAmount = 0.8f // 将遮罩层设置为更深的灰色
                      window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                    window.attributes = layoutParams
                }
            }
             dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
*/

             val btn_key = dialogView.findViewById<Button>(R.id.btn_key)
             btn_key.setOnClickListener {
                 val key = dialogView.findViewById<TextView>(R.id.key)
                 if(lifeManager.GPTenable())
                     Toast.makeText(context,"已经成功激活啦！",Toast.LENGTH_SHORT).show()
                 else {
                     if (lifeManager.keymatch(key.text.toString())) {
                         lifeManager.setGPTenable()
                         Toast.makeText(context, "激活成功！！", Toast.LENGTH_SHORT).show()

                     } else {
                         Toast.makeText(context, "密钥不对┭┮﹏┭┮", Toast.LENGTH_SHORT).show()
                     }
                 }
             }
            dialog.show()
        }


        btn_create_card.setOnClickListener {
                PhotoPickerFragment.newInstance(
                multiple = false,
                allowCamera = true,
                maxSelection = 1,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(childFragmentManager, "诗云")

        }
    }


    private fun getLastLocation() {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "没有开启定位 (╯^╰)", Toast.LENGTH_LONG).show()
           return
        }
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "没有开启GPS定位，无法自动导入位置，开启定位然后重新创建试试吧(*^O^*)y", Toast.LENGTH_LONG).show()
            return
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // 位置更新成功
                val latitude = location.latitude
                val longitude = location.longitude

                val longitudeM = convertToDMS(longitude)
                val latitudeM = convertToDMS(latitude)
                Toast.makeText(context, "经度: $longitudeM, 纬度: $latitudeM", Toast.LENGTH_SHORT).show()
                (dialoglocationEdit as TextView).text ="经度: $longitudeM, 纬度: $latitudeM"
                // 一旦获取到位置，可以停止监听
                locationManager.removeUpdates(this)

            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // 请求位置更新
        locationManager.requestLocationUpdates(

            LocationManager.GPS_PROVIDER,
            5000,
            10f,
            locationListener
        )


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(context, "首次同意，但需要开启定位，在之后的卡片才会自动输入经纬度信息 ^ω^", Toast.LENGTH_SHORT).show()
                getLastLocation()
            } else {
                Toast.makeText(context, "被拒绝啦，如果想要自动填入经纬度信息可以开启 ^ω^", Toast.LENGTH_SHORT).show()
            }
        }
    }





    private fun classifyImage(bitmap: Bitmap, spinner: Spinner) {


        // 对图像进行预处理

        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(3) // 设置返回的最大分类结果数

        val options: ImageClassifier.ImageClassifierOptions =
            optionsBuilder
                .build()

        val imageClassifier: ImageClassifier =
            ImageClassifier.createFromFileAndOptions(
                context, model_name, options
            ) // 创建 ImageClassifier 实例

        val resizedImage = createScaledBitmap(bitmap, 224, 224, true)
        val tensorImage = TensorImage.fromBitmap(resizedImage)
// 这里可能需要进行归一化处理，具体根据模型要求
// 运行推理
        val results: List<Classifications> = imageClassifier.classify(tensorImage)

        val dialogView: View = layoutInflater.inflate(R.layout.dialog_showresult, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)  // 设置自定义布局
            .create()
        dialog?.setOnShowListener { dialogInterface: DialogInterface? ->
            // 设置 dimAmount 为 0.8，暗度更高
            val window = dialog.window
            if (window != null) {
                val layoutParams = window.attributes
                layoutParams.dimAmount = 0.8f // 将遮罩层设置为更深的灰色
              //  window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                window.attributes = layoutParams
            }
        }
       // dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val mutableMap: MutableMap<String, String> = mutableMapOf(

            "Ac" to "高积云",
            "As" to "高层云",
            "Cb" to  "积雨云",
            "Cc" to "卷积云",
            "Ci" to "卷云",
            "Cs" to "卷层云",
            "Cu" to "积云",
            "Ns" to "雨层云",
            "Sc" to "层积云",
            "St" to "层云",
            "Ct" to "航迹云(IMAO)"
        )

        val Catagory_Map: MutableMap<String, Int> = mutableMapOf(

            "Ac" to 0,
            "As" to 1,
            "Cb" to 2,
            "Cc" to 3,
            "Ci" to 4,
            "Cs" to 5,
            "Cu" to 6,
            "Ns" to 7,
            "Sc" to 8,
            "St" to 9,
            "Ct" to 10
        )

        val v1 = dialogView.findViewById<TextView>(R.id.tag1)
        val v2 = dialogView.findViewById<TextView>(R.id.tag2)
        val v3 = dialogView.findViewById<TextView>(R.id.tag3)
        val v4 = dialogView.findViewById<TextView>(R.id.tag4)
        val picture_cloud = dialogView.findViewById<ImageView>(R.id.dialog_image)
        Glide.with(this)
            .load(bitmap)
            .into(picture_cloud)
        val textViews = arrayOf(v1, v2, v3, v4)
        val labelArray: MutableList<String> = mutableListOf()
        var index__ = 0
// 处理分类结果
        for (classification in results) {
            for (c in classification.categories) {
                Log.d(
                    "ImageClassifier",
                    ("Label: " + c.label).toString() + ", Confidence: " + c.score
                )
                textViews[index__].text = ("Label: " + mutableMap[c.label]).toString() + ", Confidence: " + c.score
               labelArray.add(c.label)
                index__ ++
            }
        }
        imageClassifier.close()

Log.d("INDEX",index__.toString())
Log.d("LABEL",labelArray.joinToString())
        v1.setOnClickListener{
            Catagory_Map[labelArray[0]]?.let { it1 -> spinner.setSelection(it1) }
            dialog?.dismiss()
        }

        v2.setOnClickListener {
            Catagory_Map[labelArray[1]]?.let { it1 -> spinner.setSelection(it1) }
            dialog?.dismiss()
        }
        
        v3.setOnClickListener{
            Catagory_Map[labelArray[2]]?.let { it1 -> spinner.setSelection(it1) }
            dialog?.dismiss()
        }

        v4.setOnClickListener{
            dialog?.dismiss()
        }

        dialog?.show()



    }




    private fun gpt_classify(bitmap: Bitmap,spinner: Spinner)
    {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_gptresult, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)  // 设置自定义布局
            .create()
        dialog.setCanceledOnTouchOutside(false)
        val textView = dialogView.findViewById<TextView>(R.id.tag3)

        val picture_cloud = dialogView.findViewById<ImageView>(R.id.dialog_image)
        Glide.with(this)
            .load(bitmap)
            .into(picture_cloud)


        val v4 = dialogView.findViewById<TextView>(R.id.tag4)




        val Catagory_Map: MutableMap<String, Int> = mutableMapOf(

            "高积云" to 0,
            "高层云" to 1,
            "积雨云" to 2,
            "卷积云" to 3,
            "卷云" to 4,
            "卷层云" to 5,
            "积云" to 6,
            "雨层云" to 7,
            "层积云" to 8,
            "层云" to 9,
        )




       // val BASE_URL = "https://www.gpt-plus.top"
     //val API_KEY = "sk-1D3UVT2nLXQcPuPNasQ7m9xpF6yDXOg0iSDG6bGefcHbKtwcgr"
        //val API_KEY = "sk-zk260d0b15ad823e7a10d33950800b7fe6b672e02a6eb836ab"
        //val BASE_URL = "https://api.zhizengzeng.com/"
        val BASE_URL = "https://api.chatanywhere.tech"
        val API_KEY = "sk-NzaxD7BDx7m7ealq2RyThpTVdlh1UEgDuTbiYft2bHH8jVQHabc"
        val MODEL = "gpt-4o-2024-08-06"
        val PROMPT = """分析图中是什么类型的云，返回值是两个字符串：第一个字符串为genera（高积云、高层云、积雨云、卷积云、卷云、卷层云、积云、雨层云、层积云、层云）或“不是天空”；
如果genera是卷云，第二个字符串为毛状云、钩状云、密云、堡状云、絮状云中的一个，或为空字符串
如果genera是卷积云，第二个字符串为层状云、荚状云、堡状云、絮状云中的一个，或为空字符串
如果genera是卷层云，第二个字符串为毛状云、薄幕状云中的一个，或为空字符串
如果genera是高积云，第二个字符串为层状云、荚状云、堡状云、絮状云、滚卷云中的一个，或为空字符串
如果genera是高层云，第二个字符串为空字符串
如果genera是雨层云，第二个字符串为空字符串
如果genera是层积云，第二个字符串为层状云、荚状云、堡状云、絮状云、滚卷云中的一个，或为空字符串
如果genera是层云，第二个字符串为薄幕状云、碎云中的一个，或为空字符串
如果genera是积云，第二个字符串为淡云、中云、浓云、碎云中的一个，或为空字符串
如果genera是积雨云，第二个字符串为秃状云、鬃状云中的一个，或为空字符串"""


        val base64Image = encodeBitmapToBase64(bitmap)
         val res  = sendRequest(base64Image,MODEL,PROMPT,API_KEY,BASE_URL,textView,spinner)

        textView.setOnClickListener{
            Log.d("DDD",res.first)
            if(res.first.equals("")) {
                dialog.dismiss()

            }
            else if(res.first =="不是天空") {
                dialog.dismiss()
            }
            else
            {
                Catagory_Map[res.first]?.let { it1 -> spinner.setSelection(it1) }
                    dialog?.dismiss()
            }
        }


        v4.setOnClickListener{
            dialog?.dismiss()
        }


        dialog.show()

    }





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onImagesPicked(photos: ArrayList<Uri>) {

       // val imgview = view?.findViewById<ImageView>(R.id.imgview)
        val parentDir = context?.filesDir

// 创建子文件夹
        val subFolder = File(parentDir, "DouDou")
        if (!subFolder.exists()) {
            subFolder.mkdir()  // 创建子文件夹
        }


        for ((index, photo) in photos.withIndex()){
            showClassifydialog(photo,subFolder)
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showClassifydialog(photo : Uri, subFolder: File)
    {
        // 初始话分类的界面
        val dialogView : View = layoutInflater.inflate(R.layout.dialog_classify, null)
        val dialogImageView: ImageView = dialogView.findViewById(R.id.dialog_image)
        dialoglocationEdit  = dialogView.findViewById(R.id.add_loca)
        val dialogtimeEdit = dialogView.findViewById<TextView>(R.id.add_time)
        val dialogDescriptionEdit: TextView = dialogView.findViewById(R.id.dialog_description)
        val saveButton : Button = dialogView.findViewById(R.id.save_btn)
        val cancelButton : Button =dialogView.findViewById(R.id.cancel_btn)
        val classifyButton : Button = dialogView.findViewById(R.id.classify_btn)
        val gpt_btn: Button = dialogView.findViewById(R.id.classify_btn2)
        val inputStream = context?.contentResolver?.openInputStream(photo)
        val suggestions = arrayOf("地球", "中国", "太阳系", "南极洲","银河系","马孔多","Bebop号","萤火虫号")
        val adapter_etx: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_dropdown_item_1line,
                suggestions
            )
        }
        dialoglocationEdit.setAdapter(adapter_etx)
        dialoglocationEdit.threshold = 0
        (dialoglocationEdit as TextView).text = "太阳系地球"

        try {
            val bitmap = BitmapFactory.decodeStream(inputStream)
           /* val file = File(subFolder, getFileNameWithTimestamp(photo))
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
            }*/

            Glide.with(this)
                //.asBitmap()
                .load(photo)
                .into(dialogImageView)


           /* if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
                activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE) }*/
            if (context?.let { checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            Log.d("OK","OK这里")

            } else {
                Log.d("LOOOO","查找位置启动")
                getLastLocation()
            }


            /*val autoCompleteTextView: AutoCompleteTextView =
                dialogView.findViewById(R.id.add_loca)*/
            val currentDateTime = LocalDateTime.now()


            // 定义格式化器，输出格式为 "yyyy-MM-dd HH:mm"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

            // 格式化当前时间
            val formattedDateTime = currentDateTime.format(formatter)
            dialogtimeEdit.text = formattedDateTime





            val options = mutableListOf<String>(  "高积云(Ac)",
                "高层云(As)",
                "积雨云(Cb)",
                "卷积云(Cc)",
                "卷云(Ci)",
                "卷层云(Cs)",
                "积云(Cu)",
                "雨层云(Ns)",
                "层积云(Sc)",
                "层云(St)",
                "航迹云(Ct)")
            val option_cata =  mutableListOf<String>()
                if(WikiCustomFragment.options.size == 0) {
                    Log.d("SCC","Kio的")
                    option_cata.addAll(preferencesManager.getAllGenera())
                }
                else
                {
                    option_cata.addAll(WikiCustomFragment.options)
                }
             options.addAll(option_cata)

            val adapter_sp: ArrayAdapter<String>? =
                context?.let { ArrayAdapter<String>(it, R.layout.spinner_item_layout,options ) }
               // context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, options) }
            adapter_sp?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinner: Spinner = dialogView.findViewById(R.id.spinner1)
            spinner.adapter = adapter_sp
            //spinner.setSelection(2)



            val dialog = context?.let { Dialog(it) }
            dialog?.setContentView(dialogView)
            dialog?.setOnShowListener { dialogInterface: DialogInterface? ->
                // 设置 dimAmount 为 0.8，暗度更高
                val window = dialog.window
                if (window != null) {
                    val layoutParams = window.attributes
                    layoutParams.dimAmount = 0.8f // 将遮罩层设置为更深的灰色
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                    window.attributes = layoutParams
                }
            }
            dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            // 保存数据写入数据库同时展示视图
            saveButton.setOnClickListener {
                val file = File(subFolder, getFileNameWithTimestamp(photo))
                FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream) }

                val newDescription = dialogDescriptionEdit.text.toString()
                val newttag = spinner.selectedItem
                // 更新数据
                val new_card = CloudCard(
                    0,
                    newttag.toString(),
                    file.toString(),
                    dialogtimeEdit.text.toString(),
                    dialoglocationEdit.text.toString(),
                    newDescription
                )

             /*   new_card.description = newDescription
                new_card.time = dialogtimeEdit.text.toString()
                new_card.location */

                mycareermanager.addSpecifiedGeneCloud(spinner.selectedItem.toString())
                new_card.id = dbHelper.addItem(new_card)
                adapter.add(new_card)
                sharedViewModelsub.setCloudCount(mycareermanager.getAllAtlasNum().toString())
                dialog?.dismiss()
            }

            cancelButton.setOnClickListener{
                // 需要删除图片
                dialog?.dismiss()
            }

            var label_class : String = "test"
            classifyButton.setOnClickListener{

                classifyImage(bitmap,spinner)

            }

            gpt_btn.setOnClickListener {
              //  gpt_classify(bitmap,spinner)
                if(lifeManager.GPTenable())
                gpt_classify(bitmap,spinner)
                else
                    Toast.makeText(context, "还没有激活gpt，去主界面左上角信息里查看吧~", Toast.LENGTH_SHORT).show()
            }



            Log.d("LABEL",label_class)

            dialog?.show()

        }catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close() // 关闭输入
        }


    }

    private fun getFileNameWithTimestamp(uri: Uri): String {
        val fileName = uri.lastPathSegment ?: "default_name"
        val timestamp = System.currentTimeMillis()
        return "TianTian_$timestamp"+".png"
    }

    companion object {


        @JvmStatic
        fun newInstance(number : Number) =
            GalleryFragment().apply {
                this. fragmentNumber = number
            }
    }




    fun encodeBitmapToBase64(bitmap: Bitmap): String {
        // 创建一个字节输出流
        val byteArrayOutputStream = ByteArrayOutputStream()

        // 将 Bitmap 压缩成 PNG 格式的字节数组
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        // 获取压缩后的字节数组
        val byteArray = byteArrayOutputStream.toByteArray()

        // 将字节数组编码为 Base64 字符串
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }


    private fun sendRequest( base64Image: String,MODEL:String,PROMPT:String,API_KEY:String,BASE_URL:String,textView: TextView,spinner: Spinner) : Pair<String,String> {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val jsonBody = JSONObject().apply {
            put("model", MODEL)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", JSONArray().apply {
                        put(JSONObject().apply {
                            put("type", "text")
                            put("text", PROMPT)
                        })
                        put(JSONObject().apply {
                            put("type", "image_url")
                            put("image_url", JSONObject().apply {
                                put("url", "data:image/jpeg;base64,$base64Image")
                            })
                        })
                    })
                })
            })
            put("response_format", JSONObject().apply {
                put("type", "json_schema")
                put("json_schema", JSONObject().apply {
                    put("name", "cloud_classification")
                    put("strict", true)
                    put("schema", JSONObject().apply {
                        put("type", "object")
                        put("properties", JSONObject().apply {
                            put("cloud_type", JSONObject().apply {
                                put("type", "string")
                                put("enum", JSONArray().apply {
                                    put("高积云")
                                    put("高层云")
                                    put("积雨云")
                                    put("卷积云")
                                    put("卷云")
                                    put("卷层云")
                                    put("积云")
                                    put("雨层云")
                                    put("层积云")
                                    put("层云")
                                    put("不是天空")
                                })
                            })
                            put("sub_cloud_type", JSONObject().apply {
                                put("type", "string")
                            })
                        })
                        put("additionalProperties", false)
                        put("required", JSONArray().apply {
                            put("cloud_type")
                            put("sub_cloud_type")
                        })
                    })
                })
            })
        }

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            jsonBody.toString()
        )

        val request = Request.Builder()
            .url("$BASE_URL/v1/chat/completions")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .build()

        var cloudType : String = ""
        var subCloudType : String = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {

                    Toast.makeText(context, "请求失败: ${e.message}", Toast.LENGTH_SHORT).show()
                    textView.text = "出错了,重试一下呢或者联系笔者~"


                }

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    requireActivity().runOnUiThread {

                        //  textView.text = "Response: $responseData"

                        val jsonResponse = responseData?.let { JSONObject(it) }

// 获取 "choices" 数组中的第一个元素
                        val message = jsonResponse?.getJSONArray("choices")
                            ?.getJSONObject(0)
                            ?.getJSONObject("message")
                            ?.getString("content") // 获取 content 字符串

// 将 content 字符串解析为 JSON 对象
                        val contentJson = JSONObject(message)

// 提取 "cloud_type" 和 "sub_cloud_type"
                        cloudType = contentJson.getString("cloud_type")
                        subCloudType = contentJson.getString("sub_cloud_type")

                        if(cloudType!="不是天空")
                        {
                            val Catagory_Map: MutableMap<String, Int> = mutableMapOf(

                                "高积云" to 0,
                                "高层云" to 1,
                                "积雨云" to 2,
                                "卷积云" to 3,
                                "卷云" to 4,
                                "卷层云" to 5,
                                "积云" to 6,
                                "雨层云" to 7,
                                "层积云" to 8,
                                "层云" to 9,
                            )

                            Catagory_Map[cloudType]?.let { its -> spinner.setSelection(its) }
                        }


                        textView.text = "识别结果：" + cloudType + " "+ subCloudType
                        Toast.makeText(context, "结果: $cloudType $subCloudType", Toast.LENGTH_LONG).show()
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                         textView.text = "出错了,重试一下呢或者联系笔者~"
                    }
                }
            }
        })
        Log.d("WTF",cloudType)

        return Pair(cloudType,subCloudType)
    }



}





