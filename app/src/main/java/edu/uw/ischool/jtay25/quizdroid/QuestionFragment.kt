package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class QuestionFragment : Fragment() {

    private val quizViewModel: QuizViewModel by activityViewModels()
    private val args: QuestionFragmentArgs by navArgs()

    private lateinit var questionTextView: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionTextView = view.findViewById(R.id.questionView)
        radioGroup = view.findViewById(R.id.radioGroup)
        submitButton = view.findViewById(R.id.submitBtn)

        Log.d("QuestionFragment", "Current Question Index in onViewCreated: ${quizViewModel.currentQuestionIndex}")

        quizViewModel.loadTopic(args.topicTitle)

        displayCurrentQuestion()

        submitButton.setOnClickListener {
            val selectedAnswerIndex = getSelectedAnswer()
            if (selectedAnswerIndex != null) {
                val isCorrect = quizViewModel.submitAnswer(selectedAnswerIndex)
                val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(selectedAnswerIndex)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayCurrentQuestion() {
        val question = quizViewModel.getCurrentQuestion()

        if (question != null) {
            questionTextView.text = question.text
            val radioButtons = listOf(
                view?.findViewById<RadioButton>(R.id.radioButton1),
                view?.findViewById<RadioButton>(R.id.radioButton2),
                view?.findViewById<RadioButton>(R.id.radioButton3),
                view?.findViewById<RadioButton>(R.id.radioButton4)
            )

            question.answers.forEachIndexed { index, answer ->
                radioButtons[index]?.text = answer
            }
        } else {
            Log.e("QuestionFragment", "Failed to load current question. Index out of bounds?")
        }
    }

    private fun getSelectedAnswer(): Int? {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        return when (selectedRadioButtonId) {
            R.id.radioButton1 -> 0
            R.id.radioButton2 -> 1
            R.id.radioButton3 -> 2
            R.id.radioButton4 -> 3
            else -> null
        }
    }
}
