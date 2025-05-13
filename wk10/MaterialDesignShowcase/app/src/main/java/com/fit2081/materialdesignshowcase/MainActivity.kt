package com.fit2081.materialdesignshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fit2081.materialdesignshowcase.ui.theme.MaterialDesignShowcaseTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState

// Define global variables to ensure they're available before use in onCreate
private val tabs = listOf("Home", "Edit", "Settings")
private var selectedTab = 0

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Define scope and drawerState before they're used
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            
            MaterialDesignShowcaseTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Material Design Showcase") },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            },
                            actions = {
                                // Search action button - fix the syntax
                                IconButton(
                                    onClick = { /* Search action */ }
                                ) {
                                    Icon(Icons.Default.Search, contentDescription = "Search")
                                }

                                // More options button - fix the syntax
                                IconButton(
                                    onClick = { /* More options action */ }
                                ) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            // Create navigation item for each tab
                            tabs.forEachIndexed { index, title ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            when (index) {
                                                0 -> Icons.Default.Menu
                                                1 -> Icons.Default.Edit
                                                else -> Icons.Default.Home
                                            },
                                            contentDescription = title
                                        )
                                    },
                                    label = { Text(title) },
                                    selected = selectedTab == index,
                                    onClick = {
                                        selectedTab = index
                                    }
                                )
                            }
                        }
                    },
                    // Add missing comma here
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                // Handle FAB click
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                ) { innerPadding ->
                    MaterialDesignShowcase(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MaterialDesignShowcase(
    modifier: Modifier = Modifier
) {
    // State variables for elements
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedTab = remember { mutableStateOf(0) }
    val showDialog by remember { mutableStateOf(false) }
    val textFieldValue by remember { mutableStateOf("") }
    val passwordFieldValue by remember { mutableStateOf("") }
    val sliderValue by remember { mutableStateOf(0f) }
    val switchChecked by remember { mutableStateOf(false) }

    if (showDialog) {
        // Dialog content would go here
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Drawer content would go here
        },
        content = {
            // Main content would go here
        }
    )
}