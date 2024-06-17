package com.busanit.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.busanit.community.databinding.FragmentCommonBinding


class CommonFragment : Fragment() {

    lateinit var binding:FragmentCommonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommonBinding.inflate(inflater, container, false)
        return binding.root

    }

}