package com.monke.triviamasters.ui.gameFeature.triviaFeature

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.DialogAnswerBinding
import com.monke.triviamasters.ui.uiModels.AnswerUi

/**
 * Диалог с результатом ответа
 */
class AnswerDialog: DialogFragment() {

    private var binding: DialogAnswerBinding? = null
    private var answer: AnswerUi? = null

    companion object {

        private const val ANSWER_BUNDLE_KEY = "answer bundle"

        fun newInstance(answer: AnswerUi): AnswerDialog {
            val dialog = AnswerDialog()
            dialog.arguments = bundleOf(ANSWER_BUNDLE_KEY to AnswerUi.values().indexOf(answer))
            return dialog
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAnswerBinding.inflate(inflater, container, false)
        arguments?.getInt(ANSWER_BUNDLE_KEY)?.let { answer = AnswerUi.values()[it] }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(answer) {
            AnswerUi.Right -> {
                binding?.txtAnswerStatus?.text = getString(R.string.right_answer)
                binding?.txtAnswerStatus?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.green))
            }
            AnswerUi.TimeOut -> {
                binding?.txtAnswerStatus?.text = getString(R.string.time_out)
                binding?.txtAnswerStatus?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.error))
            }
            AnswerUi.Wrong -> {
                binding?.txtAnswerStatus?.text = getString(R.string.wrong_answer)
                binding?.txtAnswerStatus?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.error))
            }
            else -> {}
        }

        binding?.btnContinue?.setOnClickListener {
            dismiss()
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as? DialogInterface.OnDismissListener)?.onDismiss(dialog)
    }

}