package com.example.splitsmart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class EmptyGroupsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the "Start a new group" button
        val startGroupButton = view.findViewById<Button>(R.id.btn_start_group)

        // Set a click listener
        startGroupButton.setOnClickListener {
            // Create an Intent to navigate to the new activity
            val intent = Intent(activity, CreateGroupActivity::class.java)
            startActivity(intent)
        }
    }
}