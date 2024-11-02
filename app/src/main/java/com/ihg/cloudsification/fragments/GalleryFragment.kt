package com.ihg.cloudsification.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.CloudCardDatabaseHelper
import com.example.database.adapter.CloudCardAdapter
import com.ihg.cloudsification.FragmentArgumentDelegate
import com.ihg.cloudsification.MainActivity
import com.ihg.cloudsification.R
import com.ihg.cloudsification.entity.CloudCard
import lv.chi.photopicker.PhotoPickerFragment
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class GalleryFragment : Fragment() , PhotoPickerFragment.Callback{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var fragmentNumber by FragmentArgumentDelegate<Number>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CloudCardAdapter
    private lateinit var dbHelper: CloudCardDatabaseHelper
    private val model_name = "model.tflite"

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

        val btn = view.findViewById<ImageButton>(R.id.imageButton)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 初始化适配器
        adapter = CloudCardAdapter(dbHelper.getAllItems(),dbHelper)
        recyclerView.adapter = adapter

    //classifyImage()

        btn.setOnClickListener {
           /* val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card0, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()*/

                PhotoPickerFragment.newInstance(
                multiple = true,
                allowCamera = true,
                maxSelection = 5,
                theme = R.style.ChiliPhotoPicker_Dark
            ).show(childFragmentManager, "诗云")

        }
    }

    private fun classifyImage() {
        // 对图像进行预处理
        val baseOptionsBuilder = BaseOptions.builder()
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(3) // 设置返回的最大分类结果数


       /* if (CompatibilityList().isDelegateSupportedOnThisDevice) {
            baseOptionsBuilder.useGpu()
            optionsBuilder.setBaseOptions(baseOptionsBuilder.build())
        }*/

        val options: ImageClassifier.ImageClassifierOptions =
            optionsBuilder
                .build()
        // Initialization
        // .setBaseOptions(baseOptionsBuilder.build())

        val imageClassifier: ImageClassifier =
            ImageClassifier.createFromFileAndOptions(
                context, model_name, options
            ) // 创建 ImageClassifier 实例

        val assetManager = activity?.assets
        var inputStream: InputStream? = null

        inputStream = assetManager?.open("images/高积云.jpg") // 读取 assets 文件
        val  bit_map = BitmapFactory.decodeStream(inputStream) // 将 InputStream 转换为 Bitmap

        inputStream?.close() // 关闭输入

        val resizedImage = createScaledBitmap(bit_map, 224, 224, true)
        val tensorImage = TensorImage.fromBitmap(resizedImage)
// 这里可能需要进行归一化处理，具体根据模型要求
// 运行推理
        val results: List<Classifications> = imageClassifier.classify(tensorImage)
// 处理分类结果
        for (classification in results) {
            for (c in classification.categories) {
                Log.d(
                    "ImageClassifier",
                    ("Label: " + c.label).toString() + ", Confidence: " + c.score
                )
                view?.findViewById<TextView>(R.id.picked_url)?.text = ("Label: " + c.label).toString() + ", Confidence: " + c.score
            }
        }
        imageClassifier.close()

    }



    override fun onImagesPicked(photos: ArrayList<Uri>) {
      /*  val picked_url = view?.findViewById<TextView>(R.id.picked_url)
        picked_url?.text = photos.joinToString(separator = "\n") { it.toString() }*/
        val imgview = view?.findViewById<ImageView>(R.id.imgview)

        val parentDir = context?.filesDir

// 创建子文件夹
        val subFolder = File(parentDir, "DouDou")
        if (!subFolder.exists()) {
            subFolder.mkdir()  // 创建子文件夹
        }


        for ((index, photo) in photos.withIndex()){
            val inputStream = context?.contentResolver?.openInputStream(photo)

            try {
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val file = File(subFolder, getFileNameWithTimestamp(photo))
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
                // val uri =FileProvider.getUriForFile(this,"com.ihg.database.fileprovider",file)

                val new_card = CloudCard(
                    0,
                    "积雨云",
                    photo.toString(),
                    "10;100",
                    "南极洲",
                    "haokan"
                )
                new_card.id = dbHelper.addItem(new_card)
                adapter.add(new_card)

                if (imgview != null) {
                    Glide.with(this)
                        .asBitmap()
                        .override(800,400)
                        .load(photo)
                        .into(imgview)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close() // 关闭输入
            }


        }




    }


    fun getFileNameWithTimestamp(uri: Uri): String {
        val fileName = uri.lastPathSegment ?: "default_name"
        val timestamp = System.currentTimeMillis()
        return "DouDou_$timestamp"+".png"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GalleryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(number : Number) =
            GalleryFragment().apply {
                this. fragmentNumber = number
            }
    }

}
