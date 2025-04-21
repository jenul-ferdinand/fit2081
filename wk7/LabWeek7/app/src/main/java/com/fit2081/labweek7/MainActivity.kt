package com.fit2081.labweek7

// !|===============================================================================================|
// !| IMPORTS
// !|===============================================================================================|

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Foundation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

// Material3
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

// Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

// My packages
import com.fit2081.labweek7.ui.theme.LabWeek7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabWeek7Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val countryCode = remember { mutableStateOf("") }
    val flagUrl = remember { mutableStateOf("") }
    val showFlag = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input field for the country code
        TextField(
            value = countryCode.value,
            onValueChange = { countryCode.value = it },
            label = { Text("Country code") },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to generate the flag URL and trigger image loading
        Button(
            onClick = {
                // Generate the flag URL for the given country code
                flagUrl.value = "https://flagcdn.com/w640/${countryCode.value.lowercase()}.jpg"
                showFlag.value = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Flag URL")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the flag image if the URL is generated
        if (showFlag.value == true) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Flag URL",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = flagUrl.value,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    AsyncImage(
                        model = flagUrl.value,
                        contentDescription = "Country Flag",
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabWeek7Theme {
        Greeting()
    }
}