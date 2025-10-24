package com.example.splitsmart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

/**
 * Adapter for displaying a list of Group objects in a RecyclerView.
 *
 * @param groups The list of Group data to display.
 */
class GroupAdapter(private val groups: List<Group>) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    // 1. ViewHolder: Holds the view references for one list item (item_group.xml)
    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groupName: TextView = view.findViewById(R.id.group_name_text)
        val groupSummary: TextView = view.findViewById(R.id.group_summary_text)
        val groupBalance: TextView = view.findViewById(R.id.group_balance_text)
        val groupIcon: TextView = view.findViewById(R.id.group_icon)
        val groupCard: MaterialCardView = view.findViewById(R.id.group_card_view) // Assuming you have an ID on your MaterialCardView
    }

    // 2. Creates the ViewHolder and inflates the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    // 3. Binds the data to the views in the ViewHolder
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]

        // Set Group Name and Summary
        holder.groupName.text = group.name
        holder.groupSummary.text = "Total: ${group.currency}${String.format("%.2f", group.totalBalance)}"

        // Set Group Icon (First letter of the group name)
        holder.groupIcon.text = group.name.firstOrNull()?.toString()?.uppercase() ?: "?"

        // Determine Color and Text for the User's Balance
        val balanceText = group.getSummary()
        holder.groupBalance.text = balanceText

        val context = holder.groupBalance.context
        val balanceColor = when {
            group.yourBalance > 0 -> ContextCompat.getColor(context, R.color.green) // You are owed, so green
            group.yourBalance < 0 -> ContextCompat.getColor(context, R.color.red)  // You owe, so red
            else -> Color.GRAY // Settled up
        }

        holder.groupBalance.setTextColor(balanceColor)

        // Handle click listeners (Future feature)
        holder.itemView.setOnClickListener {
            // FUTURE: Add code here to navigate to the detailed Group Expenses screen
        }
    }

    // 4. Returns the total number of items in the list
    override fun getItemCount() = groups.size
}
