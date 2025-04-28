package com.example.classroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.classroom.data.students.StudentsViewModel
import com.example.classroom.ui.theme.ClassroomTheme

class MainActivity : ComponentActivity() {
    val _context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Initialise the StudentsViewModel using ViewModelProvider
            // This allows the ViewModel to survive configuration changes and maintain state
            val studentViewModel: StudentsViewModel = ViewModelProvider(
                this, StudentsViewModel.StudentsViewModelFactory(this@MainActivity)
            )[StudentsViewModel::class.java]

            ClassroomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClassroomTheme {
        Greeting("Android")
    }
}