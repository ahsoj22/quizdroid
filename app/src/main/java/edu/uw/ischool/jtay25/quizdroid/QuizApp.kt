package edu.uw.ischool.jtay25.quizdroid

import android.app.Application
import android.widget.Toast

class QuizApp : Application() {

    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        topicRepository = TopicRepository(this)

        val preferences = getSharedPreferences("QuizPreferences", MODE_PRIVATE)
        val questionsUrl = preferences.getString("questionsUrl", "http://tednewardsandbox.site44.com/questions.json")

        Toast.makeText(this, "Downloading questions from: $questionsUrl", Toast.LENGTH_LONG).show()

        questionsUrl?.let { edu.uw.ischool.jtay25.quizdroid.DownloadQuestionsTask(this).execute(it) }
    }
}
