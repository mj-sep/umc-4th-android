package com.example.week6re.MainFR

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.week6re.R
import com.example.week6re.databinding.FragmentEtcBinding

class EtcFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentEtcBinding.inflate(layoutInflater).root
    }

}