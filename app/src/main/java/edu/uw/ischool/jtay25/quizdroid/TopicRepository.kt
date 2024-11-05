package edu.uw.ischool.jtay25.quizdroid

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByTitle(title: String): Topic?
}
