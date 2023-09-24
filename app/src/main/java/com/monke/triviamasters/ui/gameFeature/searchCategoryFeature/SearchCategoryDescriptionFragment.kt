package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSearchCategoryDescriptionBinding

class SearchCategoryDescriptionFragment : Fragment() {

    private var binding: FragmentSearchCategoryDescriptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCategoryDescriptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.btnStart?.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_searchCategoryDescriptionFragment_to_searchCategoryFragment)
        }
    }

}