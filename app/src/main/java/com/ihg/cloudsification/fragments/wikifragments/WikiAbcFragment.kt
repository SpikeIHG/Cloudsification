package com.ihg.cloudsification.fragments.wikifragments

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.ihg.cloudsification.R
import com.fridayof1995.tabanimation.SnapTabLayout
import com.magicgoop.tagsphere.OnTagTapListener
import com.magicgoop.tagsphere.TagSphereView
import com.magicgoop.tagsphere.item.TagItem
import com.magicgoop.tagsphere.item.TextTagItem
import java.io.IOException
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WikiAbcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WikiAbcFragment : Fragment(), OnTagTapListener {
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
        return inflater.inflate(R.layout.fragment_wiki_abc, container, false)

       /* val btn_pop = view.findViewById<ImageButton>(R.id.btn_pop)
        btn_pop.setOnClickListener{
                val bottomSheet = CustomBottomSheetFragment()
                bottomSheet.show(parentFragmentManager, "CustomBottomSheet")
        }*/

       /* val btn_dialog = view.findViewById<ImageButton>(R.id.btn_dialog)
        btn_dialog.setOnClickListener{
            showCustomDialog()
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tagView = view.findViewById<TagSphereView>(R.id.spheretagView)
        val clip0 = view.findViewById<TextView>(R.id.clip0)
        clip0.setTextIsSelectable(true)












        tagView.setTextPaint(
            TextPaint().apply {
                isAntiAlias = true
                textSize = resources.getDimension(R.dimen.tag_text_size)
                color = Color.BLACK
                typeface = context?.let { ResourcesCompat.getFont(it, R.font.d) }
            }
        )
        Log.d("SPHERE","Create SPhere")
        // 处理tag 球

        val tags = listOf("积雨云Cb", "积云Cu", "层积云Sc", "雨层云Ns","层云St","高积云Ac","高层云As","卷云Ci","卷层云Cs","卷积云Cc","特殊云Ihg")
        (0..10).map {
            TextTagItem(text = tags[it])
        }.toList().let {
            tagView.addTagList(it)
        }


        val btn_quit = view.findViewById<ImageButton>(R.id.btn_quit)
        btn_quit.setOnClickListener{
            parentFragmentManager.popBackStack()
            Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
        }

        tagView.setOnTagTapListener(this)

        val assetManager = context?.assets
        val imageViews = arrayOf(
            view.findViewById<ImageView>(R.id.catagory_img),
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
            view.findViewById<ImageView>(R.id.imag12),
            view.findViewById<ImageView>(R.id.imag13),
            view.findViewById<ImageView>(R.id.imag14),
            view.findViewById<ImageView>(R.id.imag15),
            view.findViewById<ImageView>(R.id.imag16),



            /* view.findViewById<ImageView>(R.id.catagory_img3)*/
        )

        val imageNames = arrayOf("images/level.jpg", "images/高积云.jpg","images/卷云.jpg","images/层云.jpg","images/积雨云.jpg","images/积云3.jpg","images/卷层云.jpg","images/卷云2.jpg","images/这是什么云2.jpg","images/特殊云.jpg","images/卷云3.jpg","images/这是什么云.jpg","images/晚霞1.jpg","images/高层云.jpg","images/雨层云.jpg","images/积云2.jpg","images/卷积云.jpg","images/层积云.jpg")


            var inputStream: InputStream? = null
            try {
                for (i in imageViews.indices) {
                    inputStream = assetManager?.open(imageNames[i]) // 读取 assets 文件
                    val bitmap = BitmapFactory.decodeStream(inputStream) // 将 InputStream 转换为 Bitmap
                    /*imageViews[i].setImageBitmap(bitmap)*/
                    Glide.with(this)
                        .load(bitmap)
                        .into(imageViews[i])
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close() // 关闭输入
            }








        Log.d("SPHERE","CCCDone")
    }




    private fun showCustomDialog(cloudTag : String) {
        if (cloudTag.equals("特殊云Ihg")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card0, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }

        if (cloudTag.equals("卷云Ci")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card1, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }

        if (cloudTag.equals("卷积云Cc")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card2, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()

        }

        if (cloudTag.equals("高层云As")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card3, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("积雨云Cb")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card4, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("积云Cu")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card5, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("雨层云Ns")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card6, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("高积云Ac")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card7, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("层积云Sc")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card8, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("层云St")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card9, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
        if (cloudTag.equals("卷层云Cs")) {
            val dialogView: View = layoutInflater.inflate(R.layout.wiki_dialog_card10, null)
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            dialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WikiAbcFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // 恢复视图
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ButtonListeners", "Bang Destroy!")
        activity?.findViewById<ViewPager>(R.id.viewPager)?.visibility = View.VISIBLE
        //(activity as? MainActivity)?.setViewPagerFragmentVisibility(0,true)
        activity?.findViewById<SnapTabLayout>(R.id.tabLayout)?.visibility = View.VISIBLE
        Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")

    }

    override fun onTap(tagItem: TagItem) {

        showCustomDialog((tagItem as TextTagItem).text)
       /* if ((tagItem as TextTagItem).text.equals("特殊云Ihg")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }

        if ((tagItem as TextTagItem).text.equals("卷积云Cc")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("卷云Ci")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("高层云As")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("积雨云Cb")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("积云Cu")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("雨层云Ns")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("高积云Ac")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("层积云Sc")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("层云St")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }
        if ((tagItem as TextTagItem).text.equals("卷层云Cs")) {
            Toast.makeText(
                requireContext(),
                "On tap: ${(tagItem as TextTagItem).text}",
                Toast.LENGTH_SHORT
            ).show()
        }*/

    }

}