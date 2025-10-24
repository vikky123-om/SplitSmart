package com.example.splitsmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // IMPORTANT: A flag to simulate whether the user has groups or not
    // We will use this to determine which Fragment to show initially
    private val hasGroups = true // Set this to true to test the GroupsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        // Load the initial fragment (Groups tab)
        // We check the 'hasGroups' flag to decide which fragment to load first
        if (savedInstanceState == null) {
            val initialFragment = if (hasGroups) GroupsListFragment() else EmptyGroupsFragment()
            loadFragment(initialFragment)
        }

        // 2. Setup Navigation Listener (Switching Fragments)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_groups -> {
                    // For the Groups tab, we decide based on the flag
                    val groupsFragment = if (hasGroups) GroupsListFragment() else EmptyGroupsFragment()
                    loadFragment(groupsFragment)
                    true
                }
                R.id.nav_friends -> {
                    // Load Friends fragment (Placeholder)
                    // TODO: Create and load a dedicated FriendsFragment later
                    true
                }
                R.id.nav_account -> {
                    // Load Account fragment (Placeholder)
                    // TODO: Create and load a dedicated AccountFragment later
                    true
                }
                else -> false
            }
        }

        // 3. Set up the FAB click listener
        val fabAddExpense = findViewById<FloatingActionButton>(R.id.fab_add_expense)
        fabAddExpense.setOnClickListener {
            // FUTURE STEP: Navigate to a new AddExpenseActivity
        }
    }

    /**
     * Helper function to replace the content frame with a new fragment.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content_frame, fragment)
            .commit()
    }
}