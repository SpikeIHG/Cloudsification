package com.ihg.cloudsification.fragments.wikifragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.fridayof1995.tabanimation.SnapTabLayout
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.BadgeAdapter
import com.ihg.cloudsification.adapter.BadgeManager
import com.ihg.cloudsification.adapter.CareerManager
import com.ihg.cloudsification.adapter.PreferencesManager
import com.ihg.cloudsification.adapter.SharedViewModel
import com.ihg.cloudsification.entity.Badge

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WikiBadgeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private  lateinit var badgeManager: BadgeManager
    private lateinit var careerManager: CareerManager
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var sharedViewModelsub : SharedViewModel

    lateinit var badgeRecyclerView: RecyclerView
    lateinit var badgeAdapter: BadgeAdapter
    lateinit var badges: List<Badge>
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
        return inflater.inflate(R.layout.fragment_wiki_badge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        badgeManager = context?.let { BadgeManager(it) }!!
        careerManager = CareerManager(requireContext())
        preferencesManager = PreferencesManager(requireContext())
        sharedViewModelsub = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val keys = arrayOf(
            "吃掉一朵棉花糖",
            "白色波浪",
            "雾中风景",
            "踢球的最好时光",
            "暴雨将至",
            "破碎白云之心",
            "阴天",
            "白色精灵",
            "白丝绒",
            "天空结了霜",
            "云彩收藏家",
            "我的天空里"
        )
        val desc = arrayOf(
            "发现一朵积云",
            "发现一朵层积云",
            "发现一朵层云",
            "发现一朵雨层云",
            "发现一朵积雨云",
            "发现一朵高积云",
            "发现一朵高层云",
            "发现一朵卷云",
            "发现一朵卷层云",
            "发现一朵卷积云",
            "集齐十大云属",
            "自定义一种云"
        )

        val options = mutableListOf<String>(
            "积云(Cu)",
            "层积云(Sc)",
            "层云(St)",
            "雨层云(Ns)",
            "积雨云(Cb)",
            "高积云(Ac)",
            "高层云(As)",
            "卷云(Ci)",
            "卷层云(Cs)",
            "卷积云(Cc)",
            )

        val lockedbadges = badgeManager.getUnlockedBadges()
        for( badge in lockedbadges)
        {
            var flaga = false
            when(badge){
                keys[0] -> if(careerManager.getSpecifiedGeneCloudNum(options[0]) != 0L) {
                    badgeManager.unlockSpecifiedBadge(keys[0])
                    flaga = true
                }
                keys[1] -> if(careerManager.getSpecifiedGeneCloudNum(options[1]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[1])
                    flaga = true
                }
                keys[2] -> if(careerManager.getSpecifiedGeneCloudNum(options[2]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[2])
                    flaga = true
                }
                keys[3] -> if(careerManager.getSpecifiedGeneCloudNum(options[3]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[3])
                    flaga = true
                }
                keys[4] -> if(careerManager.getSpecifiedGeneCloudNum(options[4]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[4])
                    flaga = true
                }
                keys[5] -> if(careerManager.getSpecifiedGeneCloudNum(options[5]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[5])
                    flaga = true
                }
                keys[6] -> if(careerManager.getSpecifiedGeneCloudNum(options[6]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[6])
                    flaga = true
                }
                keys[7] -> if(careerManager.getSpecifiedGeneCloudNum(options[7]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[7])
                    flaga = true
                }
                keys[8] -> if(careerManager.getSpecifiedGeneCloudNum(options[8]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[8])
                    flaga = true
                }
                keys[9] -> if(careerManager.getSpecifiedGeneCloudNum(options[9]) != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[9])
                    flaga = true
                }
                keys[10] -> if((badgeManager.getUnLockedBadgeNum() == 11L)||( badgeManager.getUnLockedBadgeNum() == 10L && !badgeManager.isUnLocked(keys[11]))){
                    badgeManager.unlockSpecifiedBadge(keys[10])
                    flaga = true
                }
                keys[11] -> if(preferencesManager.getTotalNum() != 0L){
                    badgeManager.unlockSpecifiedBadge(keys[11])
                    flaga = true
                }

            }
            if(flaga)  {
                Toast.makeText(context, "解锁徽章 $badge 啦", Toast.LENGTH_LONG).show()
                Log.d("S","解锁东西 111111111111111111111111")
                flaga = false
            }

        }


        badges = listOf(
            Badge(keys[0], desc[0], R.drawable.badge),
            Badge(keys[1], desc[1], R.drawable.badge),
            Badge(keys[2], desc[2], R.drawable.badge),
            Badge(keys[3], desc[3], R.drawable.badge),
            Badge(keys[4], desc[4], R.drawable.badge),
            Badge(keys[5], desc[5], R.drawable.badge),
            Badge(keys[6], desc[6], R.drawable.badge),
            Badge(keys[7], desc[7], R.drawable.badge),
            Badge(keys[8], desc[8], R.drawable.badge),
            Badge(keys[9], desc[9], R.drawable.badge),
            Badge(keys[10], desc[10], R.drawable.badge),
            Badge(keys[11], desc[11], R.drawable.badge),

            )


        badgeRecyclerView = view.findViewById<RecyclerView>(R.id.badge_wall)
        badgeRecyclerView.layoutManager = GridLayoutManager(context,2)

        //       badgeRecyclerView.layoutManager = LinearLayoutManager(context)
        badgeAdapter = BadgeAdapter(badges,badgeManager)
        badgeRecyclerView.adapter = badgeAdapter

        val btn_quit = view.findViewById<ImageButton>(R.id.btn_quit)
        btn_quit.setOnClickListener{
            parentFragmentManager.popBackStack()
            Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WikiBadgeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WikiBadgeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ButtonListeners", "Baged Destory")
        activity?.findViewById<ViewPager>(R.id.viewPager)?.visibility = View.VISIBLE
        //(activity as? MainActivity)?.setViewPagerFragmentVisibility(0,true)
        activity?.findViewById<SnapTabLayout>(R.id.tabLayout)?.visibility = View.VISIBLE
        Log.d("Number","the number is ${parentFragmentManager.backStackEntryCount}}")
        sharedViewModelsub.setBadgeCount(badgeManager.getUnLockedBadgeNum().toString())

    }
}