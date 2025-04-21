package com.fit2081.labweek2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

// Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Foundation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions

// Material 3
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState

// Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

// UI
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fit2081.labweek2.ui.theme.LabWeek2Theme

// Static constant variables
private const val USERNAME: String = "testUser"
private const val PASSWORD: String = "testPassword"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Main content
            LabWeek2Theme {
                Scaffold(
                    bottomBar = { BottomBar() },
                    topBar = { TopBar() },
                    floatingActionButton = { FloatingButton() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // This context will be used to show a toast message
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Image
            androidx.compose.foundation.Image(
                painter = painterResource(id  = R.drawable.image),
                contentDescription = "Testing",
                modifier = Modifier.size(200.dp)
            )

            // Spacer
            Spacer(modifier = Modifier.height(24.dp))

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Enter your username") },
                modifier = Modifier.fillMaxWidth()
            )

            // Spacer
            Spacer(modifier = Modifier.height(24.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Enter your password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // Spacer
            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = {
                    if (username == USERNAME && password == PASSWORD) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Navigate to the dashboard
                        context.startActivity(Intent(context, Dashboard::class.java))
                    } else {
                        Toast.makeText(context, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                    }

                    // Clear the username and password
                    username = ""
                    password = ""
                },
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
fun BottomBar() {
    BottomAppBar(
        modifier = Modifier.height(120.dp),
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        /* TODO ADD ACTION HERE */
                    },
                    content = {
                        Icon(Icons.Filled.Check, contentDescription = "Check Icon")
                    }
                )

                // Get current context
                val context = LocalContext.current

                IconButton(
                    onClick = {
                        context.startActivity(Intent(context, SecondActivity::class.java))
                    },
                    content = {
                        Icon(Icons.Filled.Home, contentDescription = "Go Home") 
                    }
                )

                Button(
                    onClick = {
                        /* TODO ADD ACTION HERE */
                    }
                ) {
                    Text("Click Me")
                }
            }
        }
    )
}

@Composable
fun FloatingButton() {
    FloatingActionButton(
        onClick = {

        },
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add something")
    }
}

@Composable
fun MyDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {

                },
                leadingIcon = {
                    Icon(Icons.Outlined.Edit, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = {

                },
                leadingIcon = {
                    Icon(Icons.Outlined.Settings, contentDescription = null)
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = {

                },
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = null)
                },
                trailingIcon = {
                    Text("F11", textAlign = TextAlign.Center)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    @OptIn(ExperimentalMaterial3Api::class)
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text="FooBar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressedDispatcher?.onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localised description"
                )
            }
        },
        actions = {
            MyDropdownMenu()
        },
        scrollBehavior = scrollBehaviour
    )
}