package com.monke.triviamasters.ui.loginFeature.signUpFeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.monke.triviamasters.databinding.DialogConfirmEmailBinding
import com.monke.triviamasters.ui.signUpFeature.EmailFragment
import javax.inject.Inject

class ConfirmEmailDialog: DialogFragment() {

    private var binding: DialogConfirmEmailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogConfirmEmailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}