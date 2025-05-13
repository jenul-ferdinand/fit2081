package com.fit2081.animationshowcase.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorChangeAnimationDemo() {
    // State to track the color change state
    var colorChanged by remember { mutableStateOf(false) }

    // Animated colour state that transitions between Cyan and Magenta
    val backgroundColor by animateColorAsState(
        targetValue = if (colorChanged) Color.Magenta else Color.Cyan,
        animationSpec = tween(1000),
        label = "colorAnimation"
    )

    Column {
        // Section title
        Text("Color Change Animation", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Box that changes colour when clicked
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable { colorChanged = !colorChanged }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Instruction text
        Text("Tap to change colour", fontSize = 12.sp)
    }
}