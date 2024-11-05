package edu.uw.ischool.jtay25.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {
    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "Application is starting")
        topicRepository = InMemoryTopicRepository()
    }
}