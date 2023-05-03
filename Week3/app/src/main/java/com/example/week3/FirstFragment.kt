package com.example.week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.week3.databinding.FragmentFirstBinding

class FirstFragment: Fragment() {
    private lateinit var viewBinding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentFirstBinding.inflate(layoutInflater)

        viewBinding.btnSendFrag.setOnClickListener {
            val text = viewBinding.tvEditFrag.text.toString()
            setFragmentResult("requestKey", bundleOf("bundleKey" to text))

        }
        return viewBinding.root
    }
}