package edu.uw.ischool.jtay25.quizdroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mathBtn = view.findViewById<Button>(R.id.math_Btn)
        val physicsBtn = view.findViewById<Button>(R.id.physics_Btn)
        val heroBtn = view.findViewById<Button>(R.id.hero_Btn)



        mathBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTopicOverviewFragment(1)
            findNavController().navigate(action)
        }

        physicsBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTopicOverviewFragment(2)
            findNavController().navigate(action)
        }

        heroBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTopicOverviewFragment(3)
            findNavController().navigate(action)
        }
    }
}
