package com.example.geminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.transition.CircularPropagation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


import com.example.geminiapp.ui.theme.GeminiAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GenAIScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GenAIScreen(
    modifier: Modifier = Modifier,
    genAIViewModel: GenAIViewModel = viewModel()
) {
    // String resources to be used as placeholders for prompt and result text fields
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)

    // Placeholder text for the result area
    val placeholderResult = stringResource(R.string.results_placeholder)

    // State to hold user input prompt, persisted across configuration changes
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }

    // State to hold the result text to display, persisted across configuration changes
    var result by rememberSaveable { mutableStateOf(placeholderResult) }

    // Observe the UI state from the ViewModel as a state that triggers recomposition when changed
    val uiState by genAIViewModel.uiState.collectAsState()

    // Get the current Android context
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // App title text component
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Row containing teh prompt input field and submit button
        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            // Text field for entering the AI prompt
            TextField(
                value = prompt,
                label = { Text(stringResource(R.string.prompt_label)) },
                onValueChange = { prompt = it },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            // Button to submit the prompt to the AI model
            // Disabled when prompt is empty
            Button(
                onClick = {
                    // Send the current prompt text to the ViewModel for processing
                    genAIViewModel.sendPrompt(prompt)
                },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = stringResource(R.string.action_go))
            }
        }

        // Conditional UI rendering based on the current UI state
        if (uiState is UiState.Loading) {
            // Display loading indicator when content is being generated
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            // Initialise text colour with default value
            var textColor = MaterialTheme.colorScheme.onSurface

            // Handle error state
            if (uiState is UiState.Error) {
                // Update text colour to red for error indication
                textColor = MaterialTheme.colorScheme.error

                // Update result with error message
                result = (uiState as UiState.Error).errorMessage
            }
            // Handle success state
            else if (uiState is UiState.Success) {
                // Update the text colour for success indication
                textColor = MaterialTheme.colorScheme.primary

                // Update result with generated content
                result = (uiState as UiState.Success).outputText
            }

            // Create a scrollable state for the text display area
            val scrollState = rememberScrollState()

            // Text to display either the AI-generated content or error messages
            // Supporting scrollable text area
            Text(
                text = result,
                textAlign = TextAlign.Start,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GenAIScreenPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        GenAIScreen(
            modifier = Modifier.padding(innerPadding)
        )
    }
}