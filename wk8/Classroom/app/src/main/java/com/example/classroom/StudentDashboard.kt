package com.example.classroom

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classroom.data.AuthManager
import com.example.classroom.data.quizzes.QuizAttempt
import com.example.classroom.data.quizzes.QuizAttemptViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.random.Random

@Composable
fun StudentDashboard(
    quizAttemptViewModel: QuizAttemptViewModel,
    context: Context,
    innerPadding: Dp
) {
    var q1 by remember { mutableStateOf(false) }
    var q2 by remember { mutableStateOf(false) }
    var q3 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        // Display the quiz title with styling
        Text(
            text = "FIT2081 Mobile Quiz",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        // Display instructions
        Text(
            text = "Select the correct answers to the following questions."
        )

        Spacer(modifier = Modifier.padding(20.dp))

        // Row layout for the first question with checkbox and text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = q1,
                onCheckedChange = { q1 = it }
            )

            // Display the first question text
            Text(text = "Paris is the capital of France.")
        }

        // Row layout for the second question with checkbox and text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = q2,
                onCheckedChange = { q2 = it }
            )

            // Display the first question text
            Text(text = "Vincent Van Gogh painted the Mona Lisa.")
        }

        // Row layout for the first question with checkbox and text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = q3,
                onCheckedChange = { q3 = it }
            )

            // Display the first question text
            Text(text = "Mount Kosciuszko is the highest mountain in Australia.")
        }

        Spacer(modifier = Modifier.padding(20.dp))

        // Submit button
        Button(
            onClick = {
                // Calculate the total mark based on the selected answers
                var totalMark = 0.0
                if (q1) totalMark += 1
                if (!q2) totalMark += 1
                if (q3) totalMark += 1

                // Generate a random 4-digit number for the quiz ID
                var random4digits = Random.nextInt(1000, 9999)

                // Create the quiz ID string
                var quizId = "Qz$random4digits"

                // Get the current date in dd/mm/yyyy format
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = dateFormat.format(System.currentTimeMillis())

                // Create a QuizAttempt object with student ID, quiz ID, date, and final mark
                var attempt: QuizAttempt = QuizAttempt(
                    studentId = AuthManager.getStudentId().toString(),
                    quizId = quizId,
                    quizDate = currentDate,
                    finalMark = totalMark,
                )

                // Launch a coroutine to insert the quiz attempt into the database
                CoroutineScope(Dispatchers.IO).launch {
                    quizAttemptViewModel.insertQuizAttempt(attempt)
                }

                // Reset the checkbox states after submission
                q1 = false
                q2 = false
                q3 = false

                Toast.makeText(context, "Quiz Attempt Submitted", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit Quiz Attempt")
        }

        HorizontalDivider()
    }
}