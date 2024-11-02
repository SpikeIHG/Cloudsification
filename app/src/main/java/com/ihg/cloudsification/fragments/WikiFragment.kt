package com.ihg.cloudsification.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.ihg.cloudsification.FragmentArgumentDelegate
import com.ihg.cloudsification.MainActivity
import com.ihg.cloudsification.R
import com.ihg.cloudsification.fragments.wikifragments.WikiAbcFragment
import com.ihg.cloudsification.fragments.wikifragments.WikiBadgeFragment
import com.ihg.cloudsification.fragments.wikifragments.WikiStoryFragment
import com.fridayof1995.tabanimation.SnapTabLayout


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class WikiFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var view: View

    private var fragmentNumber by FragmentArgumentDelegate<Number>()


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wiki, container, false)

        /*// 绑定各个bar的点击事件
        val cardView: CardView = view.findViewById(R.id.bar_wiki)
        cardView.setOnClickListener {
            Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
            Log.d("ButtonListener", "Button clicked!")
            // 判断是否是当前的fragment
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiAbcFragment){
                Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "aaaaaaaaaaaaaaaaaaaa!")
                if(currentFragment != null){
                    Log.d("CurrentFragment", "Visible Fragment: ${currentFragment.javaClass.simpleName}")
                }
                // 隐藏activity视图
                val newFragment = WikiAbcFragment() // 创建新的Fragment实例
               *//* val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()*//*

                val transaction = parentFragmentManager.beginTransaction()
               transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()



                hideViews();
            }
            else
            {
                Log.d("FragmentTransaction", "NewFragment is already displayed.")
            }
        }

        val cardView_story: CardView = view.findViewById(R.id.bar_story)
        cardView_story.setOnClickListener {

            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiAbcFragment){
             //   Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "bbbbbbbbbbbb!")
               *//* if(currentFragment != null){
                    Log.d("CurrentFragment", "Visible Fragment: ${currentFragment.javaClass.simpleName}")
                }*//*
                // 隐藏activity视图
                *//* val transaction = parentFragmentManager.beginTransaction()
                 transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
 // 设置动画效果

                 transaction.replace(R.id.fragment_container, newFragment) // 替换现有的Fragment
                 transaction.addToBackStack(null) // 添加到回退栈
                 transaction.commit()*//*
                val newFragment = WikiStoryFragment() // 创建新的Fragment实例
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()
                hideViews();
            }
            else
            {
                Log.d("FragmentTransaction", "NewFragment is already displayed.")
            }
        }

*/

        return view
    }


    /*fun setVisibility(isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 绑定各个bar的点击事件
        val cardView: CardView = view.findViewById(R.id.bar_wiki)
        cardView.setOnClickListener {
            Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
            Log.d("ButtonListener", "Button clicked!")
            // 判断是否是当前的fragment
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiAbcFragment){
                Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "aaaaaaaaaaaaaaaaaaaa!")
                if(currentFragment != null){
                    Log.d("CurrentFragment", "Visible Fragment: ${currentFragment.javaClass.simpleName}")
                }
                // 隐藏activity视图
                val newFragment = WikiAbcFragment() // 创建新的Fragment实例
                /* val transaction = parentFragmentManager.beginTransaction()
                 transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
 // 设置动画效果

                 transaction.replace(R.id.fragment_container, newFragment) // 替换现有的Fragment
                 transaction.addToBackStack(null) // 添加到回退栈
                 transaction.commit()*/

                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()



                hideViews();
            }
            else
            {
                Log.d("FragmentTransaction", "NewFragment is already displayed.")
            }
        }

        val cardView_story: CardView = view.findViewById(R.id.bar_story)
        cardView_story.setOnClickListener {

            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiStoryFragment){
                //   Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "bbbbbbbbbbbb!")
                /* if(currentFragment != null){
                     Log.d("CurrentFragment", "Visible Fragment: ${currentFragment.javaClass.simpleName}")
                 }*/
                // 隐藏activity视图
                /* val transaction = parentFragmentManager.beginTransaction()
                 transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
 // 设置动画效果

                 transaction.replace(R.id.fragment_container, newFragment) // 替换现有的Fragment
                 transaction.addToBackStack(null) // 添加到回退栈
                 transaction.commit()*/
                val newFragment = WikiStoryFragment() // 创建新的Fragment实例
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()
                hideViews();
            }
            else
            {
                Log.d("FragmentTransaction", "NewFragment is already displayed.")
            }
        }





        val cardView_badge: CardView = view.findViewById(R.id.bar_badge)
        cardView_badge.setOnClickListener {

            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiBadgeFragment){
                //   Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "bbbbbbbbbbbb!")
                /* if(currentFragment != null){
                     Log.d("CurrentFragment", "Visible Fragment: ${currentFragment.javaClass.simpleName}")
                 }*/
                // 隐藏activity视图
                /* val transaction = parentFragmentManager.beginTransaction()
                 transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
 // 设置动画效果

                 transaction.replace(R.id.fragment_container, newFragment) // 替换现有的Fragment
                 transaction.addToBackStack(null) // 添加到回退栈
                 transaction.commit()*/
                val newFragment = WikiBadgeFragment() // 创建新的Fragment实例
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
// 设置动画效果

                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()
                hideViews();

                refresh();
            }
            else
            {
                Log.d("FragmentTransaction", "NewFragment is already displayed.")
            }
        }











    }

    private fun refresh() {

        Log.d("refresh","我们需要更多的大奶妹")
    }


    // 用来隐藏 视图
    private fun hideViews()
    {
        // 关掉viewpager和tablayout
        (activity as? MainActivity)?.findViewById<ViewPager>(R.id.viewPager)?.visibility = View.GONE
        /*setVisibility(false);*/
        //(activity as? MainActivity)?.setViewPagerFragmentVisibility(0,false)
        (activity as? MainActivity)?.findViewById<SnapTabLayout>(R.id.tabLayout)?.visibility = View.GONE

    }









    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(number : Number) =
            WikiFragment().apply {
                this.fragmentNumber=number
                }
            }

}
