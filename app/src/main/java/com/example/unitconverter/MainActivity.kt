package com.example.unitconverter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // UI components

    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private lateinit var inputField: EditText
    private lateinit var convertButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connecting UI components to XML layout

        fromSpinner = findViewById(R.id.spinnerFrom)
        toSpinner = findViewById(R.id.spinnerTo)
        inputField = findViewById(R.id.inputValue)
        convertButton = findViewById(R.id.convertButton)
        resultText = findViewById(R.id.resultTextView)

        // spinners

        val units = arrayOf("Meter", "Centimeter", "Gram", "Kilogram")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // adapter to both spinners

        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        // button clicks
        convertButton.setOnClickListener {
            val fromUnit = fromSpinner.selectedItem.toString()
            val toUnit = toSpinner.selectedItem.toString()
            val inputValue = inputField.text.toString()

            // Checking if both units are the same

            if (fromUnit == toUnit) {
                showToast("Select different units to convert.")
                return@setOnClickListener
            }

            // Checking if input field is empty

            if (inputValue.isEmpty()) {
                showToast("Enter a value to convert.")
                return@setOnClickListener
            }

            // Checking input is a valid number

            val value = inputValue.toDoubleOrNull()
            if (value == null) {
                showToast("Please enter a valid numeric value.")
                return@setOnClickListener
            }

            // Check if the units are compatible for conversion

            if (!areUnitsCompatible(fromUnit, toUnit)) {
                showToast("Cannot convert between incompatible units.")
                return@setOnClickListener
            }

            // display the result

            val result = convertUnits(fromUnit, toUnit, value)
            resultText.text = "$result $toUnit"
        }
    }

    // Function to display a message

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun areUnitsCompatible(from: String, to: String): Boolean {
        val lengthUnits = listOf("Meter", "Centimeter")
        val weightUnits = listOf("Gram", "Kilogram")
        return (lengthUnits.contains(from) && lengthUnits.contains(to)) ||
                (weightUnits.contains(from) && weightUnits.contains(to))
    }
    // Function for converting the values

    private fun convertUnits(from: String, to: String, value: Double): Double {
        return when (from to to) {
            "Meter" to "Centimeter" -> value * 100
            "Centimeter" to "Meter" -> value / 100
            "Gram" to "Kilogram" -> value / 1000
            "Kilogram" to "Gram" -> value * 1000
            else -> value
        }
    }
}
