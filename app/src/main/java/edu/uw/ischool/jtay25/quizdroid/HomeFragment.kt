package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private lateinit var topicRepository: TopicRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicRepository = (requireActivity().application as QuizApp).topicRepository
        val topics = topicRepository.getAllTopics()

        // For each topic, create a button dynamically
        val container = view.findViewById<ViewGroup>(R.id.topic_container)
        topics.forEach { topic ->
            val button = Button(requireContext()).apply {
                text = topic.title
                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToTopicOverviewFragment(topic.title)
                    findNavController().navigate(action)
                }
            }
            container.addView(button)
        }
    }
}
