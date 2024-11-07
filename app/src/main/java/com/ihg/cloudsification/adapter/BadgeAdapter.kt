package com.ihg.cloudsification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ihg.cloudsification.R
import com.ihg.cloudsification.entity.Badge


class BadgeAdapter(
    private val badges: List<Badge>,
    private val badgeManager: BadgeManager

) : RecyclerView.Adapter<BadgeAdapter.ViewHolder>() {


    //

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val badgeIcon: ImageView = view.findViewById(R.id.badge_icon)
        val badgeName: TextView = view.findViewById(R.id.badge_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.badge_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val badge = badges[position]
        holder.badgeIcon.setImageResource(badge.drawableId)
        holder.badgeName.text = badge.name

        // 根据徽章解锁状态设置透明度
        holder.itemView.alpha = if (badgeManager.isUnLocked(badge.name)) 1.0f else 0.5f
        holder.itemView.setOnClickListener{
            val dialogView: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_badge_show, null)
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)  // 设置自定义布局
                .create()

            val name = dialogView.findViewById<TextView>(R.id.badge_name_AA)
            val desc =dialogView.findViewById<TextView>(R.id.badge_desc)
            name.text = badge.name
            desc.text =badge.description

            dialog.show()
        }
    }

    override fun getItemCount(): Int = badges.size
}
