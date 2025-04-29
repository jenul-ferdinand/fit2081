package com.example.classroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.classroom.data.quizzes.QuizAttemptViewModel
import com.example.classroom.data.students.StudentsViewModel
import com.example.classroom.ui.theme.ClassroomTheme


sealed class TeacherDashboardScreen(val route: String) {
    object AddStudent : TeacherDashboardScreen("add_student")
    object StudentList : TeacherDashboardScreen("list_student")
    object StudentAttempts : TeacherDashboardScreen("student_attempts/{id}")
}

class TeacherDashboard : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialise StudentsViewModel
        val studentsViewModel: StudentsViewModel = ViewModelProvider(
            this, StudentsViewModel.StudentsViewModelFactory(this@TeacherDashboard)
        )[StudentsViewModel::class.java]

        // Initialise QuizAttemptViewModel
        val quizAttemptViewModel: QuizAttemptViewModel = ViewModelProvider(
            this, QuizAttemptViewModel.QuizAttemptViewModelFactory(this@TeacherDashboard)
        )[QuizAttemptViewModel::class.java]

        enableEdgeToEdge()

        setContent {
            ClassroomTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ),
                            title = {
                                Text("Teacher Dashboard")
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorSCheme.primary
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                HorizontalArrangement = Arrangement.SpaceAround
                            )
                        }
                    }
                ) { innerPadding ->

                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ClassroomTheme {
        Greeting2("Android")
    }
}