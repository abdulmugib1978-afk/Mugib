package com.example.studytracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytracker.data.model.StudySession
import com.example.studytracker.ui.study.StudyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: StudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTrackerApp(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTrackerApp(viewModel: StudyViewModel) {
    val sessions by viewModel.sessionsState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Study Tracker") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add session")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (sessions.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "No study sessions yet. Tap + to add one.")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(sessions) { session ->
                                StudySessionItem(session = session, onDelete = { viewModel.deleteSession(it) })
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                AddSessionDialog(
                    onAdd = { subject, minutes ->
                        viewModel.addSession(subject, minutes)
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}

@Composable
fun StudySessionItem(session: StudySession, onDelete: (StudySession) -> Unit) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = sdf.format(Date(session.timestamp))

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = session.subjectName, style = MaterialTheme.typography.titleMedium)
                Text(text = "Duration: ${session.durationInMinutes} min", style = MaterialTheme.typography.bodyMedium)
                Text(text = date, style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = { onDelete(session) }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSessionDialog(onAdd: (String, Int) -> Unit, onDismiss: () -> Unit) {
    var subject by remember { mutableStateOf("") }
    var minutesText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Study Session") },
        text = {
            Column {
                TextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = minutesText,
                    onValueChange = { minutesText = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Duration (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val minutes = minutesText.toIntOrNull() ?: 0
                if (subject.isNotBlank() && minutes > 0) {
                    onAdd(subject.trim(), minutes)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
