package com.fit2081.currencyexchange

// !|===============================================================================================|
// !| IMPORTS
// !|===============================================================================================|

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

// Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

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

// My packages
import com.fit2081.currencyexchange.data.repository.RatesRepository
import com.fit2081.currencyexchange.ui.theme.CurrencyExchangeTheme
import kotlinx.coroutines.launch
import java.util.Locale

class CurrencyExchangeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CurrencyExchangeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// !|===============================================================================================|
// !| Currency Exchange Screen
// !|===============================================================================================|

@Composable
fun CurrencyExchangeScreen(
    modifier: Modifier = Modifier
) {
    // Creates the repository instance for fetching rates
    var repo: RatesRepository = RatesRepository()

    var baseCurrency by remember { mutableStateOf("") }
    var targetCurrency by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Creates a coroutine scope tied to the composable's lifecycle
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Base currency input
        OutlinedTextField(
            value = baseCurrency.toUpperCase(),
            onValueChange = { baseCurrency = it },
            label = { Text("Base Currency") },
            modifier = Modifier.fillMaxWidth()
        )
        // Target currency input
        OutlinedTextField(
            value = targetCurrency.toUpperCase(),
            onValueChange = { targetCurrency = it },
            label = { Text("Target Currency") },
            modifier = Modifier.fillMaxWidth()
        )
        // Amount input
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        // Submission button
        Button(
            onClick = {
                coroutineScope.launch {
                    // Get the rate for the target currency
                    val rate = repo.getRate(baseCurrency, targetCurrency)?.rates?.get(targetCurrency)

                    val amountVal = amount.toDoubleOrNull() ?: 0.0
                    result = if (rate != null) {
                        String.format(Locale.US, "%.2f", amountVal * rate)
                    } else {
                        "Currency rate not available"
                    }
                }
            }
        ) {
            Text("Get Rate")
        }

        if (result.isNotEmpty()) {
            // Display the conversion result
            Text(
                text = "Result: $result $targetCurrency",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

/**
 * ? Preview for CurrencyExchangeScreen
 */
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CurrencyExchangeScreenPreview() {
    CurrencyExchangeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            CurrencyExchangeScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}