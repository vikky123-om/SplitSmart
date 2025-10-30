package com.example.splitsmart

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.Calendar

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Link UI elements
        val nameDisplay = view.findViewById<TextView>(R.id.display_name_text) // New Display Name
        val nameEditText = view.findViewById<TextInputEditText>(R.id.name_edit_text)
        val phoneEditText = view.findViewById<TextInputEditText>(R.id.phone_edit_text)
        val dobEditText = view.findViewById<TextInputEditText>(R.id.dob_edit_text) // NEW
        val notificationsSwitch = view.findViewById<SwitchMaterial>(R.id.notifications_switch)
        val saveButton = view.findViewById<Button>(R.id.save_profile_button)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        // 2. Load User Data (Using hardcoded placeholder data)
        val defaultName = "Sam SplitSmart"
        val defaultPhone = "9876543210"
        val defaultDob = "1995-10-27"

        // Set values
        nameDisplay.text = defaultName // Set the display name beside the pic
        nameEditText.setText(defaultName)
        phoneEditText.setText(defaultPhone)
        dobEditText.setText(defaultDob) // Set DOB placeholder
        notificationsSwitch.isChecked = true

        // 3. Date Picker for DOB
        dobEditText.setOnClickListener {
            showDatePickerDialog(dobEditText, defaultDob)
        }

        // 4. Save Button Logic (Placeholder)
        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newPhone = phoneEditText.text.toString()
            val newDob = dobEditText.text.toString()
            val notificationsStatus = if (notificationsSwitch.isChecked) "ON" else "OFF"

            // Update the display name instantly
            nameDisplay.text = newName

            // Placeholder for saving data
            Toast.makeText(
                context,
                "Profile Updated: Name=$newName, DOB=$newDob, Notifications=$notificationsStatus",
                Toast.LENGTH_LONG
            ).show()
        }

        // 5. Log Out Button Logic (Simulated)
        logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    /** Shows the date picker dialog for the Date of Birth field. */
    private fun showDatePickerDialog(editText: TextInputEditText, initialDate: String) {
        val c = Calendar.getInstance()

        // Attempt to parse the existing date to set the picker's initial position
        val parts = initialDate.split("-").mapNotNull { it.toIntOrNull() }
        val year = parts.getOrElse(0) { c.get(Calendar.YEAR) }
        val month = parts.getOrElse(1) { c.get(Calendar.MONTH) + 1 } - 1 // Calendar months are 0-11
        val day = parts.getOrElse(2) { c.get(Calendar.DAY_OF_MONTH) }


        val dpd = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Date set, update the EditText
            val date = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            editText.setText(date)
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }

    /** Shows confirmation dialog before logging out (Simulated). */
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, _ ->
                // SIMULATING LOGOUT AND REDIRECT
                Toast.makeText(context, "Logged out (Simulated).", Toast.LENGTH_SHORT).show()

                // Navigate back to the LoginActivity
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}