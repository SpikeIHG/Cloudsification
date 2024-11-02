package com.ihg.cloudsification.fragments.wikifragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.ihg.cloudsification.R
import com.fridayof1995.tabanimation.SnapTabLayout
import java.io.IOException
import java.io.InputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WikiStoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_wiki_story, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_quit = view.findViewById<ImageButton>(R.id.btn_quit)
        btn_quit.setOnClickListener{
            parentFragmentManager.popBackStack()
            Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
        }

        val poet_imag_views = arrayOf(
            view.findViewById<ImageView>(R.id.poet0),
            view.findViewById<ImageView>(R.id.poet1),
             view.findViewById<ImageView>(R.id.poet2),
          view.findViewById<ImageView>(R.id.poet3),
            view.findViewById<ImageView>(R.id.poet4),
        view.findViewById<ImageView>(R.id.poet5))
        val assetManager = context?.assets
       // val inputstream : InputStream? = assetManager?.open("images/佩索阿.jpg")
        //val bitmap : Bitmap = BitmapFactory.decodeStream(inputstream)

        val imageNames = arrayOf("images/夏南.jpg", "images/佩索阿b.jpg","images/王小波a.jpg","images/徐志摩.jpg","images/雪莱.jpg","images/佚名.jpg")

        for (i in poet_imag_views.indices) {
            var inputStream: InputStream? = null
            try {
                inputStream = assetManager?.open(imageNames[i]) // 读取 assets 文件
                val bitmap = BitmapFactory.decodeStream(inputStream) // 将 InputStream 转换为 Bitmap

                Glide.with(this)
                    .load(bitmap)
                    .into(poet_imag_views[i])
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close() // 关闭输入
            }
        }

        val imageViews = arrayOf(

            view.findViewById<ImageView>(R.id.imag0),
            view.findViewById<ImageView>(R.id.imag1),
            view.findViewById<ImageView>(R.id.imag2),
            view.findViewById<ImageView>(R.id.imag3),
            view.findViewById<ImageView>(R.id.imag4),
            view.findViewById<ImageView>(R.id.imag5),
            view.findViewById<ImageView>(R.id.imag6),
            view.findViewById<ImageView>(R.id.imag7),
            view.findViewById<ImageView>(R.id.imag8),
            view.findViewById<ImageView>(R.id.imag9),
            view.findViewById<ImageView>(R.id.imag10),
            view.findViewById<ImageView>(R.id.imag11),

            /* view.findViewById<ImageView>(R.id.catagory_img3)*/
        )

        val imageName = arrayOf( "images/1.jpg","images/2.jpg","images/3.jpg","images/4.jpg","images/5.jpg","images/6.jpg","images/7.jpg","images/8.jpg","images/9.jpg","images/10.jpg","images/11.jpg","images/12.jpg")

        for (i in imageViews.indices) {
            var inputStream: InputStream? = null
            try {
                inputStream = assetManager?.open(imageName[i]) // 读取 assets 文件
                val bitmap = BitmapFactory.decodeStream(inputStream) // 将 InputStream 转换为 Bitmap
                Glide.with(this)
                    .load(bitmap)
                    .into(imageViews[i])
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close() // 关闭输入
            }
        }




    }




    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ButtonListeners", "Bang Destroy!")
        activity?.findViewById<ViewPager>(R.id.viewPager)?.visibility = View.VISIBLE
        //(activity as? MainActivity)?.setViewPagerFragmentVisibility(0,true)
        activity?.findViewById<SnapTabLayout>(R.id.tabLayout)?.visibility = View.VISIBLE
        Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")

    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WikiStoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}