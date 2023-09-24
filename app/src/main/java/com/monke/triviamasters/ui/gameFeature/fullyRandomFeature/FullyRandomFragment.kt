package com.monke.triviamasters.ui.gameFeature.fullyRandomFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.triviamasters.R

class FullyRandomFragment : Fragment() {


    private lateinit var viewModel: FullyRandomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fully_random, container, false)
    }




}