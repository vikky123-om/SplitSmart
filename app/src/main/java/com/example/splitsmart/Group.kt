package com.example.splitsmart

/**
 * Defines the data structure for a single Group object in SplitSmart.
 */
data class Group(
    val id: Int,
    val name: String,
    val totalBalance: Double, // The total balance for the group
    val yourBalance: Double,   // How much the current user owes (-) or is owed (+)
    val currency: String = "₹"
) {
    /**
     * Helper to get a simple summary string based on your balance.
     */
    fun getSummary(): String {
        return when {
            yourBalance > 0 -> "gets back $currency${String.format("%.2f", yourBalance)}"
            yourBalance < 0 -> "you owe $currency${String.format("%.2f", yourBalance * -1)}"
            else -> "you are settled up"
        }
    }
}