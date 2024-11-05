package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class TopicOverviewFragment : Fragment() {

    private val args: TopicOverviewFragmentArgs by navArgs()

    private lateinit var titleTextView: TextView
    private lateinit var shortDescriptionTextView: TextView
    private lateinit var longDescriptionTextView: TextView
    private lateinit var beginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        titleTextView = view.findViewById(R.id.titleTextView)
        shortDescriptionTextView = view.findViewById(R.id.shortDescriptionTextView)
        longDescriptionTextView = view.findViewById(R.id.longDescriptionTextView)
        beginButton = view.findViewById(R.id.beginButton)

        val topicRepository = (requireActivity().application as QuizApp).topicRepository
        val topic = topicRepository.getTopicByTitle(args.topicTitle)

        if (topic != null) {
            titleTextView.text = topic.title
            shortDescriptionTextView.text = topic.shortDescription
            longDescriptionTextView.text = topic.longDescription

            beginButton.setOnClickListener {
                val action = TopicOverviewFragmentDirections.actionTopicOverviewFragmentToQuestionFragment(topic.title)
                findNavController().navigate(action)
            }
        } else {
            // maybe log something
        }
    }
}
