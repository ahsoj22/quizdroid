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
import android.util.Log
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
        }
    }

    private fun displayAnswers() {
        val userAnswer = args.userAnswer
        val currentQuestion = quizViewModel.getCurrentQuestion()

        if (currentQuestion != null) {
            userAnswerTextView.text = "Your Answer: ${currentQuestion.answers[userAnswer]}"
            correctAnswerTextView.text = "Correct Answer: ${currentQuestion.answers[currentQuestion.correctAnswerIndex]}"
            progressTextView.text = "You have ${quizViewModel.correctAnswers} out of ${quizViewModel.totalQuestions} correct"

            if (quizViewModel.currentQuestionIndex >= quizViewModel.totalQuestions - 1) {
                nextButton.text = "Finish"
            } else {
                nextButton.text = "Next"
            }
        }
    }

    private fun handleNextOrFinish() {
        Log.d("AnswerFragment", "Current Question Index before increment: ${quizViewModel.currentQuestionIndex}")

        if (quizViewModel.currentQuestionIndex < quizViewModel.totalQuestions - 1) {
            quizViewModel.incrementQuestionIndex()

            Log.d("AnswerFragment", "Current Question Index after increment: ${quizViewModel.currentQuestionIndex}")

            val action = AnswerFragmentDirections.actionAnswerFragmentToQuestionFragment(quizViewModel.currentTopic?.title ?: "")
            findNavController().navigate(action)
        } else {
            Log.d("AnswerFragment", "Quiz complete. Resetting quiz progress.")
            quizViewModel.resetQuizProgress()
            findNavController().navigate(R.id.action_answerFragment_to_homeFragment)
        }
    }
}
