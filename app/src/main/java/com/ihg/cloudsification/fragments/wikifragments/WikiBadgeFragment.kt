package com.ihg.cloudsification.fragments.wikifragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihg.cloudsification.R
import com.ihg.cloudsification.adapter.BadgeAdapter
import com.ihg.cloudsification.entity.Badge

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WikiBadgeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WikiBadgeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_wiki_badge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var badgeRecyclerView: RecyclerView
        lateinit var badgeAdapter: BadgeAdapter
        lateinit var badges: List<Badge>

        badges = listOf(
            Badge("First Steps", "Complete your first task", R.drawable.ic_add_button),
            Badge("Achiever", "Achieve a score of 100", R.drawable.ic_add_button),
            Badge("Achiever", "Achieve a score of 100", R.drawable.ic_add_button),
            Badge("Achiever", "Achieve a score of 100", R.drawable.ic_add_button),
            Badge("Achiever", "Achieve a score of 100", R.drawable.ic_add_button),
            Badge("Achiever", "Achieve a score of 100", R.drawable.ic_add_button)

        )


        badgeRecyclerView = view.findViewById<RecyclerView>(R.id.badge_wall)
        badgeRecyclerView.layoutManager = GridLayoutManager(context,3)

        //       badgeRecyclerView.layoutManager = LinearLayoutManager(context)
        badgeAdapter = BadgeAdapter(badges)
        badgeRecyclerView.adapter = badgeAdapter


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
}