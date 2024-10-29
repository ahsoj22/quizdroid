package edu.uw.ischool.jtay25.quizdroid

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    var currentQuestionIndex: Int = 0
    var correctAnswers: Int = 0
    lateinit var questions: List<Question>
    val totalQuestions: Int
        get() = questions.size

    fun loadQuestions(topicId: Int) {
        questions = loadQuestionsForTopic(topicId)
    }


    private fun loadQuestionsForTopic(topicId: Int): List<Question> {
        return when (topicId) {
            1 -> listOf(
                Question("What is 2 + 2?", listOf("1", "2", "3", "4"), 3),
            )
            2 -> listOf(
                Question("What is the speed of light?", listOf("300m/s", "300,000km/s", "150km/s", "100km/s"), 1),
            )
            3 -> listOf(
                Question("Who is Iron Man?", listOf("Bruce Wayne", "Steve Rogers", "Tony Stark", "Clark Kent"), 2),
            )
            else -> emptyList()
        }
    }

    fun getCurrentQuestion(): Question? {
        return if (currentQuestionIndex < questions.size) questions[currentQuestionIndex] else null
    }

    fun submitAnswer(selectedAnswer: Int): Boolean {
        val question = getCurrentQuestion()
        val isCorrect = question?.correctAnswer == selectedAnswer

        if (isCorrect) correctAnswers++

        return isCorrect
    }

    fun resetQuizProgress() {
        currentQuestionIndex = 0
        correctAnswers = 0
    }
}
