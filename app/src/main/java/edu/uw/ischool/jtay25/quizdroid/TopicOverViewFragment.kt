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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

class TopicOverviewFragment : Fragment() {

    private val args: TopicOverviewFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by activityViewModels {
        QuizViewModelFactory(requireActivity().application as QuizApp)
    }
    private lateinit var titleTextView: TextView
    private lateinit var shortDescriptionTextView: TextView
    private lateinit var beginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView = view.findViewById(R.id.titleTextView)
        shortDescriptionTextView = view.findViewById(R.id.shortDescriptionTextView)
        beginButton = view.findViewById(R.id.beginButton)

        val topicRepository = (requireActivity().application as QuizApp).topicRepository
        val topic = topicRepository.getAllTopics().find { it.title == args.topicTitle }

        if (topic != null) {
            titleTextView.text = topic.title
            shortDescriptionTextView.text = topic.description

            beginButton.setOnClickListener {
                quizViewModel.loadTopic(topic.title)
                val action = TopicOverviewFragmentDirections.actionTopicOverviewFragmentToQuestionFragment(topic.title)
                findNavController().navigate(action)
            }
        } else {
            // Handle the case where the topic is not found
        }
    }
}
