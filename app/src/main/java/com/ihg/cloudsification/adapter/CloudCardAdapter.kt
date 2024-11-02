package com.example.database.adapter

import android.app.Dialog
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.CloudCardDatabaseHelper
import com.ihg.cloudsification.R
import com.ihg.cloudsification.entity.CloudCard


class CloudCardAdapter(private var items: MutableList<CloudCard>, private val atlasbase: CloudCardDatabaseHelper) : RecyclerView.Adapter<CloudCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val idview: TextView = view.findViewById(R.id.id)
        val location: TextView = view.findViewById(R.id.location)
        val time_: TextView = view.findViewById(R.id.time)
        val tag: TextView = view.findViewById(R.id.tag)
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


            holder.desc.text = items[position].description
            holder.tag.text = items[position].tag
            holder.idview.text = items[position].id.toString()
            holder.time_.text = items[position].imageUri
            holder.location.text = items[position].location


            Glide.with(holder.desc.context)
                .load(items[position].imageUri)
                .centerCrop()
                .into(holder.imageView)

            // 设置按钮点击事件
         /*   holder.deleteButton.setOnClickListener {
                val index_ = holder.adapterPosition
                if (index_ != RecyclerView.NO_POSITION) {
                    // 从数据源中删除项
                    atlasbase.deleteItem(items[index_].id)
                    items.removeAt(index_)
                    // 通知 RecyclerView 更新
                    notifyItemRemoved(index_)
                    if (index_ != (items.size + 1)) {
                        notifyItemRangeChanged(index_, items.size + 1)
                    }
                }
            }*/
            holder.deleteButton.setOnClickListener{
                alterDeleteDialog(holder)
            }

            holder.itemView.setOnClickListener {
                val dialogView : View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_check_details, null)
                val dialogImageView: ImageView = dialogView.findViewById(R.id.dialog_image)
                val dialogTitleEdit: TextView = dialogView.findViewById(R.id.dialog_title)
                val dialogDescriptionEdit: TextView = dialogView.findViewById(R.id.dialog_description)
                val saveButton : Button = dialogView.findViewById(R.id.save_btn)
                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(item.imageUri)
                    .into(dialogImageView)
                //dialogTitle.text = item.id.toString()
                //dialogDescription.text = item.description
                val suggestions = arrayOf("选项1", "选项2", "选项3", "选项4")
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    holder.itemView.context,
                    android.R.layout.simple_dropdown_item_1line,
                    suggestions
                )
                val autoCompleteTextView: AutoCompleteTextView =
                    dialogView.findViewById(R.id.autoCompleteTextView)
                autoCompleteTextView.setAdapter(adapter)
                // autoCompleteTextView.setFocusable(false)
                autoCompleteTextView.threshold = 0

                val options = arrayOf("选项1", "选项2", "选项3")
                val adapter_sp: ArrayAdapter<String> =
                    ArrayAdapter<String>(holder.itemView.context, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val spinner: Spinner = dialogView.findViewById(R.id.spinner1)
                spinner.adapter = adapter_sp



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
                    val newTitle = dialogTitleEdit.text.toString()
                    val newDescription = dialogDescriptionEdit.text.toString()

                    val newtt = spinner.selectedItem
                    // 更新数据
                   // item.tag = newtt.toString()
                    item.description = newDescription
                    notifyItemChanged(position)

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
                items.removeAt(index_)
                // 通知 RecyclerView 更新
                notifyItemRemoved(index_)
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

