package com.ihg.cloudsification

import android.view.View
import java.util.*

/**
 * Created by Depression on 10-08-2018.  需要一个父类的fragment manager 直接support的那个
 */
class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<androidx.fragment.app.Fragment>()

    override fun getItem(position: Int): androidx.fragment.app.Fragment {                   // 翻页的时候一直调用的这个
        if(position == 1){

        }
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: androidx.fragment.app.Fragment) {
        mFragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun addFragmentAt(fragment: androidx.fragment.app.Fragment, position: Int) {
        mFragmentList.add(position, fragment)
        notifyDataSetChanged()
    }

    fun setVisibility(position: Int,boolean: Boolean){
        if(boolean)
        mFragmentList[position].view?.visibility = View.VISIBLE
        else
            mFragmentList[position].view?.visibility = View.GONE
    }



    companion object {
        private val TAG = "ViewPagerAdapter"
    }
}


// 滑动到新的界面的销毁问题 以及新的fragment的初始化问题