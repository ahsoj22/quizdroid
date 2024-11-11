package edu.uw.ischool.jtay25.quizdroid

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class TopicRepository(private val context: Context) {

    private val topics: List<Topic> by lazy {
        loadTopicsFromJson()
    }

    fun getAllTopics(): List<Topic> {
        return topics
    }

    private fun loadTopicsFromJson(): List<Topic> {
        val jsonString = loadJsonFromAssets("questions.json")
        return if (jsonString != null) {
            val listType = object : TypeToken<List<TopicJson>>() {}.type
            val topicsFromJson: List<TopicJson> = Gson().fromJson(jsonString, listType)

            topicsFromJson.map {
                Topic(
                    title = it.title,
                    description = it.desc,
                    questions = it.questions.map { questionJson ->
                        Question(
                            text = questionJson.text,
                            answers = questionJson.answers,
                            correctAnswerIndex = questionJson.answer.toInt()
                        )
                    }
                )
            }
        } else {
            emptyList()
        }
    }

    private fun loadJsonFromAssets(filename: String): String? {
        return try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    data class TopicJson(
        val title: String,
        val desc: String,
        val questions: List<QuestionJson>
    )

    data class QuestionJson(
        val text: String,
        val answer: String,
        val answers: List<String>
    )
}
