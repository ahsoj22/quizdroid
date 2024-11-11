package edu.uw.ischool.jtay25.quizdroid

data class Topic(
    val title: String,
    val description: String,
    val questions: List<Question>
)

