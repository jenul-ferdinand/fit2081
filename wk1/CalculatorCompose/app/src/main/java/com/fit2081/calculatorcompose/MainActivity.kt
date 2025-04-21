package com.fit2081.calculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

// Units of Measurement
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Material UI Theme
import androidx.compose.material3.*

// Material Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode

// Haptic feedback
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

import com.fit2081.calculatorcompose.ui.theme.CalculatorComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(true) }

            CalculatorComposeTheme(darkTheme = darkTheme) {
                Scaffold(
                    // TOP APP BAR
                    topBar = {
                        TopAppBar(
                            title = { Text("Calculator") },
                            actions = {
                                IconButton(onClick = { darkTheme = !darkTheme }) {
                                    Icon(
                                        imageVector = if (darkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                        contentDescription = "Toggle Theme"
                                    )

                                }
                            }
                        )

                    }

                ) { innerPadding ->
                    // MAIN CONTENT
                    CalculatorLayout(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun CalculatorLayout(modifier: Modifier = Modifier) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }

    // Centered vertical column to hold the calculator elements
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input field for the first number
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp)
        )

        // Input field for the second number
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp)
        )

        // Text to show the result
        Text(
            text = "Result: $result",
            style = TextStyle(fontSize = 24.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        // Row to hold the buttons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            val haptic = LocalHapticFeedback.current

            // Add button
            Button(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                calculate("+", num1, num2)?.let {
                    result = it
                }
            }) {
                Text("+")
            }

            // Subtract button
            Button(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                calculate("-", num1, num2)?.let {
                    result = it
                }
            }) {
                Text("-")
            }

            // Multiply button
            Button(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                calculate("*", num1, num2)?.let {
                    result = it
                }
            }) {
                Text("*")
            }

            // Clear button
            Button(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                num1 = ""
                num2 = ""
                result = "0"
            }) {
                Text("Clear")
            }
        }
    }
}

// Function to calculate/clear the result based on the operate
fun calculate(op: String, num1Str: String, num2Str: String): String? {
    val number1 = num1Str.toDoubleOrNull()
    val number2 = num2Str.toDoubleOrNull()

    if (number1 == null || number2 == null) {
        return null
    }

    return when (op) {
        "+" -> (number1 + number2).toString()
        "-" -> (number1 - number2).toString()
        "*" -> (number1 * number2).toString()
        else -> "0"
    }
}

