package com.ihg.cloudsification.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.ihg.cloudsification.FragmentArgumentDelegate
import com.ihg.cloudsification.MainActivity
import com.ihg.cloudsification.R
import com.ihg.cloudsification.fragments.wikifragments.WikiAbcFragment
import com.ihg.cloudsification.fragments.wikifragments.WikiBadgeFragment
import com.ihg.cloudsification.fragments.wikifragments.WikiStoryFragment
import com.fridayof1995.tabanimation.SnapTabLayout
import com.ihg.cloudsification.adapter.BadgeManager
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.fragments.wikifragments.WikiCustomFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class WikiFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var view: View
    private lateinit var sharedViewModel: SharedViewModel
    private  lateinit var  mycaeermanager : CareerManager
    private lateinit var badgeManager: BadgeManager

    private var fragmentNumber by FragmentArgumentDelegate<Number>()
private lateinit var preferencesManager: PreferencesManager

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
        Log.d("CCCCCCCXXXXX","1")


        return view
    }


    /*fun setVisibility(isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CCCCCCCXXXXX","2")
        mycaeermanager = context?.let   { CareerManager(it) }!!
        preferencesManager = context?.let   { PreferencesManager(it) }!!
        badgeManager = BadgeManager(requireContext())
        val gene_num = view.findViewById<TextView>(R.id.howmanycustom)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        gene_num.text = preferencesManager.getTotalNum().toString()  + " 种"
        val cloud_num = view.findViewById<TextView>(R.id.howmanycloud)
        val badge_num = view.findViewById<TextView>(R.id.howmanybadge)
        val cloud_most_recorded = view.findViewById<TextView>(R.id.whichcloud)
        if(mycaeermanager.getMaxNumGene().isNotEmpty())
            cloud_most_recorded.text = mycaeermanager.getMaxNumGene()
        else
            cloud_most_recorded.text = "还没有记录~~~"
        badge_num.text = badgeManager.getUnLockedBadgeNum().toString() +" 个"
        cloud_num.text = mycaeermanager.getAllAtlasNum().toString() + " 朵"
        sharedViewModel.geneCountLiveData.observe(viewLifecycleOwner, Observer { gcount ->
            // 更新 TextView
            Log.d("SHA","Do the share change")
                gene_num.text = gcount + " 种"
        })

        sharedViewModel.cloudCountLiveData.observe(viewLifecycleOwner, Observer { count ->
            // 更新 TextView
            cloud_num.text = count + " 朵"
        })

        sharedViewModel.badgeCountLiveData.observe(viewLifecycleOwner, Observer { bcount ->
            // 更新 TextView
            badge_num.text = bcount + " 个"
        })

        sharedViewModel.CloudMostRecordedLiveData.observe(viewLifecycleOwner, Observer { most ->
            // 更新 TextView
            cloud_most_recorded.text = most
        })

        val cnum_btn = view.findViewById<ImageButton>(R.id.cloudnum_btn)
        val mostcloud_btn = view.findViewById<ImageButton>(R.id.clouds_ccc)
        val badgenum_btn = view.findViewById<ImageButton>(R.id.badge_btn)
        val customnum_btn = view.findViewById<ImageButton>(R.id.customnum_btn)

        badgenum_btn.setOnClickListener {
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiBadgeFragment){
                val newFragment = WikiBadgeFragment() // 创建新的Fragment实例
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()
                hideViews();
            }
        }
        customnum_btn.setOnClickListener {
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiCustomFragment){
                val newFragment = WikiCustomFragment() // 创建新的Fragment实例
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                transaction.replace(R.id.fragment_container1, newFragment) // 替换现有的Fragment
                transaction.addToBackStack(null) // 添加到回退栈
                transaction.commit()
                hideViews();
            }
        }
        // 绑定各个bar的点击事件
        val cardView: CardView = view.findViewById(R.id.bar_wiki)
        cardView.setOnClickListener {
            // 判断是否是当前的fragment
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiAbcFragment){

                // 隐藏activity视图
                val newFragment = WikiAbcFragment() // 创建新的Fragment实例


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


        val cardView_custom: CardView = view.findViewById(R.id.bar_custom)
        cardView_custom.setOnClickListener {

            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragment_container1)
            if(currentFragment !is WikiCustomFragment){
                //   Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
                Log.d("ButtonListeners", "bbbbbbbbbbbb!")

                val newFragment = WikiCustomFragment() // 创建新的Fragment实例
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


    override fun onStart() {
        super.onStart()
        Log.d("CCCCCCCXXXXX","3")

    }

    override fun onResume() {
        super.onResume()
        Log.d("CCCCCCCXXXXX","4")

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
