package com.example.geminiapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenAIViewModel: ViewModel() {
    /**
     * * Mutable state flow to hold the current UI state
     *
     * Initally set to `UiState.Initial`
     */
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey,
    )

    /**
     * * Function to send a prompt to the generative AI model
     *
     * Updates the UI state based on the response
     *
     * @param prompt The input text prompt to be sent to the generative model
     */
    fun sendPrompt(
        prompt: String
    ) {
        // Set the UI state to Loading before making the API call
        _uiState.value = UiState.Loading

        // Launch a coroutine in teh IO dispatcher to perform the API call
        viewModelScope.launch(Dispatchers.IO) {
             try {
                 // Generate content using the generative model
                 val response = generativeModel.generateContent(
                     content {
                         text(prompt) // Set the input text for the model
                     }
                 )

                 // Update the UI state with the generated content if successful
                 response.text?.let { outputContent ->
                     _uiState.value = UiState.Success(outputContent)
                 }
             } catch (e: Exception) {
                 // Update the UI state with an error message if an exception occurs
                 _uiState.value = UiState.Error(e.localizedMessage ?: "")
             }
        }
    }
}