package edu.uw.ischool.jtay25.quizdroid


data class Question(
    val text: String,
    val correctAnswerIndex: Int,
    val answers: List<String>
)
