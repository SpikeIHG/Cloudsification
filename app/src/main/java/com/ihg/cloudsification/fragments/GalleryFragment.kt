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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.CloudCardDatabaseHelper
import com.ihg.cloudsification.adapter.CloudCardAdapter
import com.ihg.cloudsification.FragmentArgumentDelegate
import com.ihg.cloudsification.MainActivity
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.convertToDMS
import com.ihg.cloudsification.entity.CloudCard
import com.ihg.cloudsification.fragments.wikifragments.WikiCustomFragment
import lv.chi.photopicker.PhotoPickerFragment
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        sharedViewModelsub = ViewModelProvider(requireActivity()).get(com.ihg.cloudsification.adapter.SharedViewModel::class.java)
        val btn_create_card = view.findViewById<ImageButton>(R.id.imageButton)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 初始化适配器
         preferencesManager = context?.let { PreferencesManager(it) }!!
        adapter = preferencesManager?.let { CloudCardAdapter(dbHelper.getAllItems(),dbHelper, it,mycareermanager,sharedViewModelsub) }!!
        recyclerView.adapter = adapter


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
            Toast.makeText(context, "没有开启GPS定位服务，无法自动导入位置，下拉菜单开启吧(*^O^*)y", Toast.LENGTH_LONG).show()
            return
        }

        Log.d("LOOOO","开始进行查询")
        // 定义位置监听器

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // 位置更新成功
                val latitude = location.latitude
                val longitude = location.longitude

                val longitudeM = convertToDMS(longitude)
                val latitudeM = convertToDMS(latitude)
                Toast.makeText(context, "经度: $longitudeM, 纬度: $latitudeM", Toast.LENGTH_LONG).show()
                (dialoglocationEdit as TextView).text ="经度: $longitudeM, 纬度: $latitudeM"

                Log.d("LOOOO","经度: $longitudeM, 纬度: $latitudeM")
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
                //Toast.makeText(context, "首次同意，出于某种什么原因，在之后的卡片才会自动输入 ^ω^", Toast.LENGTH_SHORT).show()
                getLastLocation()
            } else {
                Toast.makeText(context, "被拒绝啦，如果想要自动填入经纬度信息可以开启 ^ω^", Toast.LENGTH_SHORT).show()
            }
        }
    }





    private fun classifyImage(bitmap: Bitmap, spinner: Spinner) {


        // 对图像进行预处理
        var label_result : String =""
        val baseOptionsBuilder = BaseOptions.builder()
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
        val inputStream = context?.contentResolver?.openInputStream(photo)
        val suggestions = arrayOf("地球", "中国", "太阳系", "南极洲","银河系","马孔多","Bebop号")
        val adapter_etx: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_dropdown_item_1line,
                suggestions
            )
        }
        dialoglocationEdit.setAdapter(adapter_etx)
        dialoglocationEdit.threshold = 0

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


            if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
                activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE) }
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

}
