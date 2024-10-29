package edu.uw.ischool.jtay25.quizdroid

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)