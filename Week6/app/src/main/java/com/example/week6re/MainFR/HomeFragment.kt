package com.example.week6re.MainFR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.week6re.HomeFR.HomeVPAdapter
import com.example.week6re.R
import com.example.week6re.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    /*
    private val viewBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    */

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // var view: View = FragmentHomeBinding.inflate(layoutInflater).root
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)

        tabLayout = view.findViewById(R.id.tab_main)
        viewPager = view.findViewById(R.id.vp_main)

        val adapter = HomeVPAdapter(this@HomeFragment)
        viewPager.adapter = adapter

        val tabTitleArray = arrayOf("One", "Two", "Three")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        return view
    }

}