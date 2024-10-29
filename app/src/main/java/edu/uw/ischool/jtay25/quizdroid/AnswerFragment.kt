package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.activityViewModels

class AnswerFragment : Fragment() {

    private val args: AnswerFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by activityViewModels()

    private lateinit var userAnswerTextView: TextView
    private lateinit var correctAnswerTextView: TextView
    private lateinit var progressTextView: TextView
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAnswerTextView = view.findViewById(R.id.userAnswerTextView)
        correctAnswerTextView = view.findViewById(R.id.correctAnswerTextView)
        progressTextView = view.findViewById(R.id.progressTextView)
        nextButton = view.findViewById(R.id.nextButton)

        displayAnswers()

        nextButton.setOnClickListener {
            handleNextOrFinish()
            quizViewModel.resetQuizProgress()
        }
    }

    private fun displayAnswers() {
        // Display the received question text, user's answer, and correct answer
        userAnswerTextView.text = "Your Answer: ${args.options[args.userAnswer]}"
        correctAnswerTextView.text = "Correct Answer: ${args.options[args.correctAnswer]}"
        progressTextView.text = "You have ${args.progress} out of ${args.totalQuestions} correct"

        // Update the button text
        if (args.progress == args.totalQuestions) {
            nextButton.text = "Finish"
        } else {
            nextButton.text = "Next"
        }
    }

    private fun handleNextOrFinish() {
        if (args.progress < args.totalQuestions) {
            findNavController().navigate(R.id.action_answerFragment_to_questionFragment)
        } else {
            findNavController().navigate(R.id.action_answerFragment_to_HomeFragment)
        }
    }


}
