package edu.uw.ischool.jtay25.quizdroid

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)
