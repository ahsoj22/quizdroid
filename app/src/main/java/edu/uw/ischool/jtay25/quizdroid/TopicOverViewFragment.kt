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

class TopicOverViewFragment : Fragment(){
    private val args: TopicOverViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topicId = args.topicId
        val beginBtn = view.findViewById<Button>(R.id.begin_Btn)
        val descOutput = view.findViewById<TextView>(R.id.descView)
        val totalQuestionsText = view.findViewById<TextView>(R.id.total_Questions)
        when (topicId) {
            1 -> {
                descOutput.text = "This is the Math topic. It covers basic math concepts."
                totalQuestionsText.text = "Total Questions: 1"
            }
            2 -> {
                descOutput.text = "This is the Physics topic. It covers fundamental physics concepts."
                totalQuestionsText.text = "Total Questions: 1"
            }
            3 -> {
                descOutput.text = "This is the Marvel Superheroes topic. It covers characters in the Marvel universe."
                totalQuestionsText.text = "Total Questions: 1"
            }
        }
        beginBtn.setOnClickListener{
            val action = TopicOverViewFragmentDirections.actionTopicOverviewFragmentToQuestionFragment(topicId)
            findNavController().navigate(action)
        }
    }
}