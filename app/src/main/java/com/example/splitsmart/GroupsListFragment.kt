package com.example.splitsmart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Get reference to the RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.groups_recycler_view)

        // 2. Create sample data for testing
        val sampleGroups = listOf(
            Group(1, "Weekend Trip", 2500.00, 500.00),         // User is owed (Green)
            Group(2, "Roommates Rent", 15000.00, -250.00),    // User owes (Red)
            Group(3, "Family Dinner", 1200.00, 0.00),         // Settled (Gray)
            Group(4, "Office Lunch", 850.50, 15.25)
        )

        // 3. Set the layout manager (how the list is arranged)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 4. Create and set the adapter with the sample data
        val adapter = GroupAdapter(sampleGroups)
        recyclerView.adapter = adapter
    }
}
