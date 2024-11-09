package com.ihg.cloudsification.adapter

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.CloudCardDatabaseHelper
import com.ihg.cloudsification.R
import com.ihg.cloudsification.entity.CloudCard
import com.ihg.cloudsification.fragments.wikifragments.WikiCustomFragment
import java.io.File


class CloudCardAdapter(private var items: MutableList<CloudCard>, private val atlasbase: CloudCardDatabaseHelper,private var preferencesManager: PreferencesManager,private var careerManager: CareerManager,
                       private  val sharedViewModelsub: SharedViewModel
                        ) : RecyclerView.Adapter<CloudCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val labelview: TextView = view.findViewById(R.id.label)
        val location: TextView = view.findViewById(R.id.location)
        val time_: TextView = view.findViewById(R.id.time)
        val desc: TextView = view.findViewById(R.id.description)

        val deleteButton: ImageButton = view.findViewById(R.id.deletebutton) // 假设有一个删除按钮





        /*fun bind(item: CloudCard) {*/
        /*val a = adapterPosition*/
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.cloudcard_layout, parent, false)
            return ViewHolder(view)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            /* // 设置删除按钮的点击事件
        holder.deleteButton.setOnClickListener {
            onDelete(item.id)
            val position = adapterPosition
        }*/

            // 绑定数据到 ViewHolder
            // ...
            val item = items[position]

            if(item.description == "")
            {
                holder.desc.text = "\t\t\t\t彩云易散琉璃脆"
            }
            else
            {
                holder.desc.text = items[position].description
            }

            if(item.location == "")
            {
                holder.location.text = "宁静号"
            }
            else
            {
                holder.location.text = items[position].location
            }

            if(item.time == "")
            {
                holder.time_.text = "某个美好的晴天"
            }
            else
            {
                holder.time_.text = items[position].time
            }



            holder.labelview.text = items[position].tag
           // holder.time_.text = items[position].time
           // holder.location.text = items[position].location


            Glide.with(holder.desc.context)
                .load(items[position].imageUri)
                .centerCrop()
                .into(holder.imageView)

            holder.deleteButton.setOnClickListener{
                alterDeleteDialog(holder)
            }

            holder.itemView.setOnClickListener {
                val dialogView : View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_check_details, null)
                val dialogImageView: ImageView = dialogView.findViewById(R.id.dialog_image)
                val dialogDescriptionEdit: TextView = dialogView.findViewById(R.id.dialog_description)
                val saveButton : Button = dialogView.findViewById(R.id.save_btn)
                val cancelButton : Button = dialogView.findViewById(R.id.cancel_btn)
                val show_time : TextView = dialogView.findViewById(R.id.this_time)
                val show_location : TextView = dialogView.findViewById(R.id.this_loca)
                show_time.text = item.time
                show_location.text = item.location
                if(dialogDescriptionEdit.text != null)
                {
                    dialogDescriptionEdit.text = item.description
                }

                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(item.imageUri)
                    .into(dialogImageView)

               /* val suggestions = arrayOf("选项1", "选项2", "选项3", "选项4")
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    holder.itemView.context,
                    android.R.layout.simple_dropdown_item_1line,
                    suggestions
                )*/
             /*   val autoCompleteTextView: AutoCompleteTextView =
                    dialogView.findViewById(R.id.autoCompleteTextView)
                autoCompleteTextView.setAdapter(adapter)
                // autoCompleteTextView.setFocusable(false)
                autoCompleteTextView.threshold = 0*/

                val options = mutableListOf<String>( "高积云(Ac)",
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
                val option_cata = mutableListOf<String>()
                if(WikiCustomFragment.options.size == 0) {
                    Log.d("SCC","Kio的")
                    option_cata.addAll(preferencesManager.getAllGenera())
                }
                else
                {
                    option_cata.addAll(WikiCustomFragment.options)
                }
                options.addAll(option_cata)
                // options.addAll(WikiCustomFragment.options)
                val adapter_sp: ArrayAdapter<String> =
                    ArrayAdapter<String>(holder.itemView.context, R.layout.show_spinner_layout, options)
                adapter_sp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                val spinner: Spinner = dialogView.findViewById(R.id.spinner0)
                spinner.adapter = adapter_sp

                val selectedItem = item.tag
                val positionx = adapter_sp.getPosition(selectedItem)
                spinner.setSelection(positionx)
                val dialog = Dialog(holder.itemView.context)
                dialog.setContentView(dialogView)
                /* .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                 .create()*/

                dialog.setOnShowListener { dialogInterface: DialogInterface? ->
                    // 设置 dimAmount 为 0.8，暗度更高
                    val window = dialog.window
                    if (window != null) {
                        val layoutParams = window.attributes
                        layoutParams.dimAmount = 0.8f // 将遮罩层设置为更深的灰色
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                        window.attributes = layoutParams
                    }
                }
                dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

                saveButton.setOnClickListener {
                  //  val newTitle = dialogTitleEdit.text.toString()
                    val newDescription = dialogDescriptionEdit.text.toString()

                    val newtt = spinner.selectedItem
                    Log.d("SSS",newtt.toString())

                    if(!newtt.toString().equals(item.tag))
                    {
                        careerManager.addSpecifiedGeneCloud(newtt.toString())
                        careerManager.declineSpecifiedGeneCloud(item.tag)
                    }

                    item.tag = newtt.toString()
                    item.description = newDescription
                    notifyItemChanged(position)
                    atlasbase.updateItem(item)
                    dialog.dismiss()
                }
                cancelButton.setOnClickListener{
                    dialog.dismiss()
                }

                dialog .show()
            }

        }

    private fun alterDeleteDialog(holder: ViewHolder) {
        val dialogView: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_confirmdelete, null)
        val dialog = AlertDialog.Builder(holder.itemView.context)
            .setView(dialogView)  // 设置自定义布局
            .create()

        val btn_yep = dialogView.findViewById<Button>(R.id.yep)
        val btn_nop = dialogView.findViewById<Button>(R.id.nope)
        btn_yep?.setOnClickListener{
            val index_ = holder.adapterPosition
            if (index_ != RecyclerView.NO_POSITION) {
                // 从数据源中删除项
                atlasbase.deleteItem(items[index_].id)

                val file = File(items[index_].imageUri)
                if (file.exists()) {
                   file.delete()
                }
                if(!careerManager.declineSpecifiedGeneCloud(items[index_].tag))
                    Log.d("XX","可以的负数")
                items.removeAt(index_)
                // 通知 RecyclerView 更新
                notifyItemRemoved(index_)

                sharedViewModelsub.setCloudCount(careerManager.getAllAtlasNum().toString())

                if (index_ != (items.size + 1)) {
                    notifyItemRangeChanged(index_, items.size + 1)
                }
            }

            dialog.dismiss()
        }
        btn_nop?.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }


    fun add(card: CloudCard)
        {
            items.add(card)
            notifyItemInserted(items.size)
        }


        override fun getItemCount() = items.size

        fun updateItems(newItems: MutableList<CloudCard>) {
            items = newItems
            notifyDataSetChanged()
        }

    }

