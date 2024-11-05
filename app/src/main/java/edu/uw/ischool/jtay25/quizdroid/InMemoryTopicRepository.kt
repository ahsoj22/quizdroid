package edu.uw.ischool.jtay25.quizdroid

class InMemoryTopicRepository : TopicRepository {

    private val topics = listOf(
        Topic(
            title = "Math",
            shortDescription = "Basic Math Problems",
            longDescription = "This topic covers basic math operations.",
            questions = listOf(
                Question("What is 2 + 2?", listOf("1", "2", "3", "4"), 3),
                Question("What is 5 - 3?", listOf("1", "2", "3", "4"), 1)
            )
        ),
        Topic(
            title = "Physics",
            shortDescription = "Fundamental Physics Concepts",
            longDescription = "This topic covers basic concepts in physics.",
            questions = listOf(
                Question("What is the speed of light?", listOf("300m/s", "300,000km/s", "150km/s", "100km/s"), 1),
                Question("What is gravity?", listOf("6.674×10⁻¹¹", "9.8m/s²", "3.14", "1.61"), 0)
            )
        ),
        Topic(
            title = "Marvel Superheroes",
            shortDescription = "Facts about Marvel superheroes",
            longDescription = "This topic covers basic facts about popular Marvel superheroes.",
            questions = listOf(
                Question("Who is Iron Man?", listOf("Bruce Wayne", "Steve Rogers", "Tony Stark", "Clark Kent"), 2),
                Question("Who is Spider-Man?", listOf("Bruce Banner", "Peter Parker", "Tony Stark", "Clark Kent"), 1)
            )
        )
    )

    override fun getAllTopics(): List<Topic> = topics

    override fun getTopicByTitle(title: String): Topic? {
        return topics.find { it.title == title }
    }
}
