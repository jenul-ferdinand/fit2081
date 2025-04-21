package com.example.tennisplayermanagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tennisplayermanagement.database.Player
import com.example.tennisplayermanagement.database.PlayerViewModel
import com.example.tennisplayermanagement.ui.theme.TennisPlayerManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val myViewModel: PlayerViewModel = ViewModelProvider(this, PlayerViewModel.PlayerViewModelFactory(this@MainActivity))[PlayerViewModel::class.java]

            TennisPlayerManagementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddTennisPlayer(this@MainActivity, innerPadding, myViewModel)
                }
            }
        }
    }
}


@Composable
fun AddTennisPlayer(
    context: Context,
    paddingValues: PaddingValues,
    viewModel: PlayerViewModel
) {
    var playerName by remember { mutableStateOf(TextFieldValue("")) }
    var playerRanking by remember { mutableStateOf(TextFieldValue("")) }
    var topVenue by remember { mutableStateOf(TextFieldValue("")) }
    val listOfPlayers by viewModel.allPlayers.collectAsState(initial = emptyList())

    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Player Management",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PlayerFormSection(
            playerName = playerName,
            playerRanking = playerRanking,
            topVenue = topVenue,
            onNameChange = { playerName = it },
            onRankingChange = { playerRanking = it },
            onVenueChange = { topVenue = it }
        )

        ActionButtons(
            onAdd = {
                // Create a new player
                viewModel.insertPlayer(
                    Player(
                        name = playerName.text,
                        ranking = playerRanking.text.toInt(),
                        topVenue = topVenue.text
                    )
                )
            },
            onDeleteAll = {
                // Delete all players
                viewModel.deleteAllPlayers()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(16.dp))

        PlayerList(
            players = listOfPlayers,
            context = context,
            viewModel = viewModel
        )
    }
}

@Composable
fun PlayerFormSection(
    playerName: TextFieldValue,
    playerRanking: TextFieldValue,
    topVenue: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    onRankingChange: (TextFieldValue) -> Unit,
    onVenueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = playerName,
        onValueChange = onNameChange,
        label = { Text("Player Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )

    OutlinedTextField(
        value = playerRanking,
        onValueChange = onRankingChange,
        label = { Text("Player Ranking") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    OutlinedTextField(
        value = topVenue,
        onValueChange = onVenueChange,
        label = { Text("Top Venue") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun ActionButtons(
    onAdd: () -> Unit,
    onDeleteAll: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Add button
        Button(
            onClick = onAdd,
            modifier = Modifier.weight(1f)
        ) {
            Text("Add Player")
        }

        // Delete all button
        Button(
            onClick = onDeleteAll,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Delete Player")
        }
    }
}

@Composable
fun PlayerList(
    players: List<Player>,
    context: Context,
    viewModel: PlayerViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
       items(players) { player ->
           PlayerCard(
               player = player,
               context = context,
               onDelete = {
                   viewModel.deletePlayerById(player.id)
               }
           )
       }
    }
}

@Composable
fun PlayerCard(
    player: Player,
    context: Context,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Name: ${player.name}",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ranking: ${player.ranking}"
                )

                Text(
                    text = "Top Venue: ${player.topVenue}"
                )
            }

            Column {
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete Player",
                        tint = Color.Red
                    )
                }

                IconButton(
                    onClick = {
                        // Sharing player information
                        val messageToShare = "Player: ${player.name}, Ranking: ${player.ranking}, Top Venue: ${player.topVenue}"

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, messageToShare)
                        }

                        context.startActivity(Intent.createChooser(intent, "Share Player Info"))
                    }
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share Player",
                        tint = Color.Blue
                    )
                }
            }
        }
    }
}

@Composable
fun AddTennisPlayer() {

}