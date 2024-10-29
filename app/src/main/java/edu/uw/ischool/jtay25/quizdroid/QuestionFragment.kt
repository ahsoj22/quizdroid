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
import android.widget.RadioButton
import android.util.Log
import android.widget.Toast

class QuestionFragment : Fragment(){
    private val args: QuestionFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by activityViewModels()
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
        submitButton = view.findViewById(R.id.submitBtn)
        questionTextView = view.findViewById(R.id.questionView)
        option1 = view.findViewById(R.id.radioButton1)
        option2 = view.findViewById(R.id.radioButton2)
        option3 = view.findViewById(R.id.radioButton3)
        option4 = view.findViewById(R.id.radioButton4)

        quizViewModel.loadQuestions(args.topicId)
        displayCurrentQuestion()
        submitButton.setOnClickListener {
            val selectedAnswer = getSelectedAnswer()
            if (selectedAnswer != null) {
                val isCorrect = quizViewModel.submitAnswer(selectedAnswer)
                navigateToAnswerFragment(isCorrect, selectedAnswer)
            }
        }
    }
    private fun displayCurrentQuestion() {
        val question = quizViewModel.getCurrentQuestion()
        question?.let {
            questionTextView.text = it.text
            option1.text = it.options[0]
            option2.text = it.options[1]
            option3.text = it.options[2]
            option4.text = it.options[3]
        }
    }
    private fun setupRadioButtonSelection() {
        val radioButtons = listOf(option1, option2, option3, option4)

        for (radioButton in radioButtons) {
            radioButton.setOnClickListener {
                // Deselect other RadioButtons
                radioButtons.forEach { it.isChecked = false }
                // Select the clicked RadioButton
                radioButton.isChecked = true
            }
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
        val currentQuestion = quizViewModel.getCurrentQuestion()

        if (currentQuestion != null) {
            val questionText = currentQuestion.text
            val correctAnswerIndex = currentQuestion.correctAnswer
            val options = currentQuestion.options.toTypedArray()

            // Log for debugging
            Log.d("QuestionFragment", "Navigating to AnswerFragment with question: $questionText")
            Log.d("QuestionFragment", "Correct answer index: $correctAnswerIndex")
            Log.d("QuestionFragment", "Options: ${options.joinToString(", ")}")

            val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(
                userAnswer = selectedAnswer,
                correctAnswer = correctAnswerIndex,
                options = options,
                progress = quizViewModel.correctAnswers,
                totalQuestions = quizViewModel.totalQuestions
            )
            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "Error: No current question found", Toast.LENGTH_SHORT).show()
        }
    }


}