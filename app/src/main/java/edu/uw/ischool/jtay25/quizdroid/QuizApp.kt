package edu.uw.ischool.jtay25.quizdroid

import android.app.Application

class QuizApp : Application() {

    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        topicRepository = TopicRepository(applicationContext)
    }
}
