package edu.uw.ischool.jtay25.quizdroid

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    var currentQuestionIndex: Int = 0
    var correctAnswers: Int = 0
    lateinit var questions: List<Question>
    lateinit var currentTopic: Topic
    val totalQuestions: Int
        get() = questions.size

    fun loadTopic(topicTitle: String) {
        if (this::questions.isInitialized && currentTopic?.title == topicTitle) {
            return
        }

        val repository = InMemoryTopicRepository()
        val topic = repository.getTopicByTitle(topicTitle)
        if (topic != null) {
            currentTopic = topic
            questions = topic.questions
            currentQuestionIndex = 0
            correctAnswers = 0
        }
    }

    fun submitAnswer(selectedAnswer: Int): Boolean {
        val correct = questions[currentQuestionIndex].correctAnswerIndex == selectedAnswer
        if (correct) correctAnswers++
        return correct
    }

    fun incrementQuestionIndex() {
        currentQuestionIndex++
    }

    fun getCurrentQuestion(): Question? {
        return if (currentQuestionIndex < questions.size) questions[currentQuestionIndex] else null
    }

    fun resetQuizProgress() {
        currentQuestionIndex = 0
        correctAnswers = 0
    }
}
