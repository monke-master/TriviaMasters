package com.monke.triviamasters.ui.loginFeature.signUpFeature

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.monke.triviamasters.databinding.DialogConfirmEmailBinding
import com.monke.triviamasters.ui.signUpFeature.EmailFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmEmailDialog: DialogFragment() {

    private val viewModel: EmailViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emailConfirmed.collect { isConfirmed ->
                    if (isConfirmed)
                        dismiss()
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
    }


}