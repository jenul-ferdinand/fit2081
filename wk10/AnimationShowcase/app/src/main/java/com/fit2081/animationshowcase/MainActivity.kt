package com.fit2081.animationshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fit2081.animationshowcase.composables.ColorChangeAnimationDemo
import com.fit2081.animationshowcase.composables.FadeAnimationDemo
import com.fit2081.animationshowcase.composables.SizeAnimationDemo
import com.fit2081.animationshowcase.composables.SpringAnimationDemo
import com.fit2081.animationshowcase.ui.theme.AnimationShowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AnimationShowcaseTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimationDemoScreen()
                }
            }
        }
    }
}

@Composable
fun AnimationDemoScreen() {
    // Create a scroll state to enable vertical scrolling through demos
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Title for the animation examples screen
        Text(
            text = "Animation Examples",
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        FadeAnimationDemo()

        ColorChangeAnimationDemo()

        SizeAnimationDemo()

        SpringAnimationDemo()
    }
}