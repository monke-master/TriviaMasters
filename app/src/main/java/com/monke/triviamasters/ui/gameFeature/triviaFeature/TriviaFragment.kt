package com.monke.triviamasters.ui.gameFeature.triviaFeature

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentTriviaBinding
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.QUESTION_TIME_MILLIS
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.uiModels.AnswerUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class TriviaFragment : Fragment(), DialogInterface.OnDismissListener {

    @Inject
    lateinit var factory: TriviaViewModel.Factory
    private val viewModel: TriviaViewModel by viewModels { factory }

    private var binding: FragmentTriviaBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTriviaBinding.inflate(inflater, container, false)
        (parentFragment?.parentFragment as GameFragment).gameComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.game.collect { game ->
                    game?.let {
                        setupQuestionDescription(game)
                        viewModel.startQuestionTime()
                        binding?.editTextAnswer?.setText("")
                    }

                }
            }
        }
        // Конец игры
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.endOfGame.collect {

                }
            }
        }
        setupProgressBar()
        setupAnswerButton()
        setupAnswerEditText()
    }

    private fun setupQuestionDescription(game: Game) {
        val title = getString(R.string.question) +
                " ${game.currentQuestionNumber}/${game.questionsList.size}"
        binding?.toolbar?.title = title
        binding?.chipCategory?.text = game.currentQuestion.category.title
        binding?.chipPrice?.text = game.currentQuestion.price.toString()
        binding?.txtQuestion?.text = game.currentQuestion.title
    }

    private fun setupProgressBar() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.time.collect { time ->
                    if (time == 0L) {
                        val dialog = AnswerDialog.newInstance(AnswerUi.TimeOut)
                        dialog.show(childFragmentManager, dialog.tag)
                    }
                    binding?.progressBar?.progress = (time.toFloat() / QUESTION_TIME_MILLIS * 100).toInt()
                }
            }
        }
    }

    private fun setupAnswerEditText() {
        binding?.editTextAnswer?.setText(viewModel.answer)
        binding?.editTextAnswer?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let {
                    viewModel.answer = text.toString()
                }
            }
        })
    }

    private fun setupAnswerButton() {
        binding?.btnAnswer?.setOnClickListener {
            val isAnswerCorrect = viewModel.answerQuestion()
            val answerUi = if (isAnswerCorrect) AnswerUi.Right else AnswerUi.Wrong
            val dialog = AnswerDialog.newInstance(answerUi)
            dialog.show(childFragmentManager, dialog.tag)

        }
    }

    override fun onDismiss(p0: DialogInterface?) {
        viewModel.nextQuestion()
    }
}
