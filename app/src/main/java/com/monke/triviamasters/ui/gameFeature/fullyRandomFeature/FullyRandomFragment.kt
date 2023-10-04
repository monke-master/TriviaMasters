package com.monke.triviamasters.ui.gameFeature.fullyRandomFeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentFullyRandomBinding
import com.monke.triviamasters.ui.components.LoadingDialog
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullyRandomFragment : Fragment() {


    @Inject
    lateinit var factory: FullyRandomViewModel.Factory
    private val viewModel: FullyRandomViewModel by viewModels { factory }

    private var binding: FragmentFullyRandomBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullyRandomBinding.inflate(inflater, container, false)
        (activity as MainActivity).gameComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        UiState.Loading -> showLoadingDialog()
                        is UiState.Error -> Toast.makeText(
                            requireContext(),
                            state.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        is UiState.Success -> view.findNavController()
                            .navigate(R.id.action_fullyRandomFragment_to_questionFragment)
                        else -> {}
                    }
                }
            }
        }
        binding?.btnStart?.setOnClickListener {
            viewModel.createGame()
        }
    }

    private fun showLoadingDialog() {
        val dialog = LoadingDialog()
        dialog.show(parentFragmentManager, dialog.tag)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state !is UiState.Loading)
                        dialog.dismiss()
                }
            }
        }
    }


}