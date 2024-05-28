package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nim.Level

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onLevelSelected: (level: Level, stacksCount: Int, candiesCount: Int) -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(Level.EASY) }
    CenterColumn(modifier) {
        Title()
        if (showSettings) {
            SettingsSection(modifier = Modifier.width(150.dp)) { stacksCount, numOfCandies ->
                onLevelSelected(selectedLevel, stacksCount, numOfCandies)
            }
        } else {
            LevelSelection(Modifier.width(150.dp)) {
                selectedLevel = it
                showSettings = true
            }
        }
    }
}

@Composable
fun LevelTile(item: Level, onClick: (level: Level) -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = { onClick(item) }) {
        Text(item.text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text("NIM", style = MaterialTheme.typography.h1)
}

@Composable
fun SettingsSection(modifier: Modifier = Modifier, onPlay: (stacksCount: Int, numOfCandies: Int) -> Unit) {
    var stacksCount by remember { mutableStateOf("3") }
    var numberOfCandies by remember { mutableStateOf("30") }
    CenterColumn(modifier = modifier) {
        OutlinedTextField(
            stacksCount.toString(),
            onValueChange = { stacksCount = it },
            label = { Text("Number of stacks") })
        OutlinedTextField(
            numberOfCandies.toString(),
            onValueChange = { numberOfCandies = it },
            label = { Text("Number of candies") },
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Button(onClick = { onPlay(stacksCount.toInt(), numberOfCandies.toInt()) }, modifier = Modifier.fillMaxWidth()) {
            Text("Play")
        }
    }
}

@Composable
fun LevelSelection(modifier: Modifier = Modifier, onLevelSelected: (level: Level) -> Unit) {
    CenterColumn(modifier = modifier) {
        Level.entries.forEach {
            LevelTile(it, onLevelSelected)
        }
    }
}