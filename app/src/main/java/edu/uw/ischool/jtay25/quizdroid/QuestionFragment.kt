package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.ViewModelProvider

class QuestionFragment : Fragment() {

    private val args: QuestionFragmentArgs by navArgs()
    private lateinit var quizViewModel: QuizViewModel

    private lateinit var questionTextView: TextView
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as QuizApp
        val factory = QuizViewModelFactory(app)
        quizViewModel = ViewModelProvider(requireActivity(), factory).get(QuizViewModel::class.java)

        questionTextView = view.findViewById(R.id.questionView)
        option1 = view.findViewById(R.id.radioButton1)
        option2 = view.findViewById(R.id.radioButton2)
        option3 = view.findViewById(R.id.radioButton3)
        option4 = view.findViewById(R.id.radioButton4)
        submitButton = view.findViewById(R.id.submitBtn)

        val currentQuestion = quizViewModel.getCurrentQuestion()

        currentQuestion?.let {
            questionTextView.text = it.text
            option1.text = it.answers[0]
            option2.text = it.answers[1]
            option3.text = it.answers[2]
            option4.text = it.answers[3]
        }

        submitButton.setOnClickListener {
            val selectedAnswer = when {
                option1.isChecked -> 0
                option2.isChecked -> 1
                option3.isChecked -> 2
                option4.isChecked -> 3
                else -> -1
            }

            if (selectedAnswer != -1) {
                val isCorrect = quizViewModel.submitAnswer(selectedAnswer)
                val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(
                    userAnswer = selectedAnswer,
                    correctAnswer = currentQuestion?.correctAnswerIndex ?: -1,
                    progress = quizViewModel.correctAnswers,
                    totalQuestions = quizViewModel.totalQuestions
                )
                findNavController().navigate(action)
            }
        }
    }
    private fun displayCurrentQuestion() {
        val question = quizViewModel.getCurrentQuestion()
        question?.let {
            questionTextView.text = it.text
            option1.text = it.answers[0]
            option2.text = it.answers[1]
            option3.text = it.answers[2]
            option4.text = it.answers[3]
        }
    }

    private fun getSelectedAnswer(): Int? {
        return when {
            option1.isChecked -> 0
            option2.isChecked -> 1
            option3.isChecked -> 2
            option4.isChecked -> 3
            else -> null
        }
    }

    private fun navigateToAnswerFragment(isCorrect: Boolean, selectedAnswer: Int) {
        val currentQuestion = quizViewModel.getCurrentQuestion() ?: return
        val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(
            userAnswer = selectedAnswer,
            correctAnswer = currentQuestion.correctAnswerIndex,
            progress = quizViewModel.correctAnswers,
            totalQuestions = quizViewModel.totalQuestions
        )
        findNavController().navigate(action)
    }
}
