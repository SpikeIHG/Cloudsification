package com.ihg.cloudsification.fragments.wikifragments

import android.app.AlertDialog

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.fridayof1995.tabanimation.SnapTabLayout
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.CustomCloudAdapter
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.entity.CustomCloud
import com.magicgoop.tagsphere.OnTagTapListener
import com.magicgoop.tagsphere.TagSphereView
import com.magicgoop.tagsphere.item.TagItem
import com.magicgoop.tagsphere.item.TextTagItem



class WikiCustomFragment : Fragment() , OnTagTapListener {


    lateinit var tagView : TagSphereView
    lateinit var preferencesManager : PreferencesManager
    lateinit var adapter_sp: ArrayAdapter<String>

    private lateinit var sharedViewModelsub: SharedViewModel
    private lateinit var careerManagersub: CareerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wiki_custom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModelsub = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        careerManagersub = context?.let { CareerManager(it) }!!
         preferencesManager = context?.let { PreferencesManager(it) }!!

         tagView = view.findViewById<TagSphereView>(R.id.customSphere)
        tagView.setTextPaint(
            TextPaint().apply {
                isAntiAlias = true
                textSize = resources.getDimension(R.dimen.custome_tag_text_size)
                color = Color.BLACK
                typeface = context?.let { ResourcesCompat.getFont(it, R.font.d) }
            }
        )


        /* val tags = listOf("积雨云Cb", "积云Cu", "层积云Sc", "雨层云Ns","层云St","高积云Ac","高层云As","卷云Ci","卷层云Cs","卷积云Cc","特殊云Ihg")
       val taglist = (0..10).map {
            TextTagItem(text = tags[it])
            }.toList()

            tagView.addTagList(taglist)
*/
        preferencesManager?.getAllGenera()?.forEach { value -> tagView.addTag(TextTagItem(value)) }
        tagView.setOnTagTapListener(this)

        /* val btn_change = view.findViewById<Button>(R.id.change_btn)
        btn_change.setOnClickListener{
            *//*tagView.removeTag(taglist[0])
            tagView.removeTag(taglist[1])
            tagView.removeTag(taglist[2])
            tagView.removeTag(taglist[3])*//*

        }
*/
         options = preferencesManager?.getAllGenera()?.toMutableList() ?: mutableListOf<String>()

         adapter_sp  =
             context?.let { ArrayAdapter<String>(it, R.layout.spinner_item_layout_custom, options) }!!

        adapter_sp?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner: Spinner = view.findViewById(R.id.option_gene)
        spinner.adapter = adapter_sp
        val gene_spe = spinner.selectedItem

        val save_genera = view.findViewById<Button>(R.id.save_gene_cus)
        val save_species = view.findViewById<Button>(R.id.save_spe_cus)

        val gene_name = view.findViewById<TextView>(R.id.gene_name)
        val gene_abbr = view.findViewById<TextView>(R.id.gene_abbr)
        val gene_desc = view.findViewById<TextView>(R.id.gene_desc)

        val spe_name = view.findViewById<TextView>(R.id.species_first)
        val spe_abbr = view.findViewById<TextView>(R.id.species_name)
        val spe_desc = view.findViewById<TextView>(R.id.species_desc)
        save_genera.setOnClickListener {

                val dialogViewG: View = layoutInflater.inflate(R.layout.dialog_confirm_custom_gen, null)
                val dialog = AlertDialog.Builder(context)
                    .setView(dialogViewG)  // 设置自定义布局
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

                val confirm_btn = dialogViewG.findViewById<Button>(R.id.yepG)
                confirm_btn.setOnClickListener {
                    val key = gene_name.text.toString() + gene_abbr.text.toString()
                   /* if (gene_name.text.toString() == "")
                        Toast.makeText(context, "没有输入云属的名字~~ (╯^╰)", Toast.LENGTH_LONG).show()*/
                  /*  else {*/
                        val justdoforit = preferencesManager?.getSpecies(key)
                        if ( justdoforit != null && justdoforit.size != 0) {

                            preferencesManager.saveGeneraSpecies(key, justdoforit)
                        } else {
                            preferencesManager?.saveGeneraSpecies(key, arrayOf(""))
                        }
                  /*  if ( justdoforit != null && justdoforit.size != 0) {

                        preferencesManager.saveGeneraSpecies(key, justdoforit)
                    } else {
                        preferencesManager?.saveGeneraSpecies(key, arrayOf(""))
                    }*/
                        // 保存描述
                        preferencesManager?.saveDescription(key, gene_desc.text.toString())

                    Toast.makeText(context, "云属创建成功~~(> 0 <)~~", Toast.LENGTH_LONG).show()
                        tagView.addTag(TextTagItem(key.toString()))
                    options.add(key)
                    adapter_sp?.notifyDataSetChanged()
                    dialog.dismiss()
                }
               // }

                val cancel_btn = dialogViewG.findViewById<Button>(R.id.nopeG)
                cancel_btn.setOnClickListener{
                    dialog.dismiss()
                }

            if (gene_name.text.toString() == "")
                Toast.makeText(context, "没有输入云属的名字~~ (╯^╰)", Toast.LENGTH_LONG).show()
            else
            dialog.show()

        }
            save_species.setOnClickListener {
                val key = spinner.selectedItem               // 种类名
                val num = preferencesManager.getSpecies("Test")?.size
                Log.d("SSS","多少个 $num")
                if (gene_spe == null)
                    Toast.makeText(context, "还没有创建任何云属~~ (╯^╰)", Toast.LENGTH_LONG).show()
                else if(spe_name.text.toString() == "")
                    Toast.makeText(context, "还没有输入云朵的名字~~ (╯^╰)", Toast.LENGTH_LONG).show()
                else {
                    val dialogView: View = layoutInflater.inflate(R.layout.dialog_confirm_custom, null)
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
                    val save_save = dialogView.findViewById<Button>(R.id.yep)

                    save_save.setOnClickListener{
                        val fullname = spe_name.text.toString()+"  " +spe_abbr.text.toString()
                        val justdoforit = preferencesManager?.getSpecies(key.toString())
                        if (justdoforit != null && justdoforit.isNotEmpty()) {
                            val list = justdoforit.toMutableList()
                            list.add(fullname)
                            preferencesManager.saveGeneraSpecies(key.toString(), list.toTypedArray())
                        } else {
                            preferencesManager?.saveGeneraSpecies(key.toString(), arrayOf(fullname))
                        }
                        preferencesManager?.saveDescription(fullname+ "_S", spe_desc.text.toString())
                        Toast.makeText(context, "新云种创建成功~~(> 0 <)~~", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }

                    val cancel_save = dialogView.findViewById<Button>(R.id.nope)
                    cancel_save.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show()


                }


            }
            // end of save_species

            val viewSwitcher = view.findViewById<ViewSwitcher>(R.id.viewSwitcher)

            val btn_species = view.findViewById<Button>(R.id.switch_btn_species)
            btn_species.setOnClickListener {
                Log.d("XXXX","1111111")
                viewSwitcher.displayedChild = 1
            }
            val btn_gene = view.findViewById<Button>(R.id.switch_btn_gene)
            btn_gene.setOnClickListener {

                viewSwitcher.displayedChild = 0
            }

            val btn_quit = view.findViewById<ImageButton>(R.id.btn_quit)
            btn_quit.setOnClickListener {
                parentFragmentManager.popBackStack()
                Log.d("Number", "the number is ${parentFragmentManager.backStackEntryCount}}")
            }


        }








    companion object {

         var options : MutableList<String> = mutableListOf<String>()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WikiCustomFragment().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }


    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("ButtonListeners", "Bang Destroy!")
        activity?.findViewById<ViewPager>(R.id.viewPager)?.visibility = View.VISIBLE
        //(activity as? MainActivity)?.setViewPagerFragmentVisibility(0,true)
        activity?.findViewById<SnapTabLayout>(R.id.tabLayout)?.visibility = View.VISIBLE
        Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
        Log.d("Number","Do the livedata")
        sharedViewModelsub.setGeneCount(preferencesManager.getTotalNum().toString())

    }

    override fun onTap(tagItem: TagItem) {

        /*showCustomCloud((tagItem as TextTagItem))*/
        val text: String = (tagItem as TextTagItem).text
        val all_species = preferencesManager.getSpecies(text)
        val gene_desc = preferencesManager.getDescription(text)

        val dialogView: View = layoutInflater.inflate(R.layout.dialog_gene_and_spe, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)  // 设置自定义布局
            .create()

        /*  val dialog = context?.let { Dialog(it) }
          dialog?.setContentView(dialogView)
          dialog?.setOnShowListener { dialogInterface: DialogInterface? ->
              // 设置 dimAmount 为 0.8，暗度更高
              val window = dialog.window
              if (window != null) {
                  val layoutParams = window.attributes
                  layoutParams.dimAmount = 0.8f // 将遮罩层设置为更深的灰色
                  window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                  window.attributes = layoutParams
              }
          }
          dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)*/


        val display_name = dialogView.findViewById<TextView>(R.id.display_gene_name)
        val display_desc = dialogView.findViewById<TextView>(R.id.display_gene_description)
        display_name.text = text
        display_desc.text = gene_desc





        val clear_btn = dialogView.findViewById<Button>(R.id.clear_all)
        clear_btn.setOnClickListener {
            val dialogConfirm: View = layoutInflater.inflate(R.layout.dialog_clear_all, null)
            val dialogClear = AlertDialog.Builder(context)
                .setView(dialogConfirm)  // 设置自定义布局
                .create()

            val yep_btn = dialogConfirm.findViewById<Button>(R.id.yepC)
            val nop_btn = dialogConfirm.findViewById<Button>(R.id.nopeC)
            yep_btn.setOnClickListener {

                preferencesManager.removeGeneraFromPreferences(text)
                tagView.removeTag(tagItem)
                careerManagersub.removeGene(text)

                options.remove(text)
                adapter_sp?.notifyDataSetChanged()
                Toast.makeText(context, "已删除~~(> 0 <)~~", Toast.LENGTH_LONG).show()
                dialogClear.dismiss()
                dialog.dismiss()


            }
            nop_btn.setOnClickListener {
                dialogClear.dismiss()
            }

            dialogClear.show()

        }

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.clouds_ccc)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val cloudlist = mutableListOf<CustomCloud>()
        all_species?.forEach { value ->
            preferencesManager.getDescription(value+"_S")
                ?.let { CustomCloud(value, it) }?.let { cloudlist.add(it) }
        }

        val adapter = CustomCloudAdapter(cloudlist)
        recyclerView.adapter = adapter

        dialog?.show()

    }



}