package edu.uw.ischool.jtay25.quizdroid

import androidx.lifecycle.ViewModel

class QuizViewModel(app: QuizApp) : ViewModel() {

    private val topicRepository = app.topicRepository

    var currentQuestionIndex: Int = 0
    var correctAnswers: Int = 0
    lateinit var currentTopic: Topic

    val totalQuestions: Int
    get() = currentTopic.questions.size

    fun loadTopic(topicTitle: String) {
        currentTopic = topicRepository.getAllTopics().find { it.title == topicTitle }
            ?: throw IllegalArgumentException("Topic not found")
        currentQuestionIndex = 0
        correctAnswers = 0
    }

    fun getCurrentQuestion() = currentTopic.questions.getOrNull(currentQuestionIndex)

    fun submitAnswer(selectedAnswer: Int): Boolean {
        val correct = currentTopic.questions[currentQuestionIndex].correctAnswerIndex == selectedAnswer
        if (correct) correctAnswers++
        return correct
    }

    fun incrementQuestionIndex() {
        currentQuestionIndex++
    }

    fun isQuizFinished() = currentQuestionIndex >= currentTopic.questions.size
}
