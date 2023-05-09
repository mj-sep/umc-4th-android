package com.example.week6re.MainFR

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week6re.R
import com.example.week6re.SettingFR.SettingVPAdapter
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3


class SettingFragment : Fragment() {
    var currentPosition=0
    private lateinit var viewPager: ViewPager2
    val handler= Handler(Looper.getMainLooper()){
        setPage()
        true
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // var view: View = FragmentHomeBinding.inflate(layoutInflater).root
        var view: View = inflater.inflate(R.layout.fragment_setting, container, false)


        viewPager = view.findViewById(R.id.vp_main)

        val adapter = SettingVPAdapter(this@SettingFragment)
        viewPager.adapter = adapter

        // val indicator: CircleIndicator = view.findViewById(R.id.indicator)
        // val indicator = view.findViewById<View>(R.id.indicator) as CircleIndicator
        val indicator: CircleIndicator3 = view.findViewById(R.id.indicator)
        indicator.setViewPager(viewPager)

        val thread = Thread(PagerRunnable())
        thread.start()

        return view
    }

    //페이지 변경하기
    fun setPage(){
        if(currentPosition==5) currentPosition=0
        viewPager.setCurrentItem(currentPosition,true)
        currentPosition+=1
    }

    // 2초마다 페이지 넘기기
    inner class PagerRunnable:Runnable{
        override fun run() {
            while(true){
                Thread.sleep(2000)
                handler.sendEmptyMessage(0)
            }
        }
    }
}

