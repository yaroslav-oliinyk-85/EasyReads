package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalChangeDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalScreen(
    navToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadingGoalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showReadingGoalChangeDialog by remember { mutableStateOf(false) }

    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            ReadingGoalTopAppBar()
        },
        content = { paddingValues ->
            ReadingGoalContent(
                uiState = uiState,
                onChangeGoalClick = {
                    showReadingGoalChangeDialog = true
                },
                onBookClick = { navToBookDetails(it.id.toString()) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )

    // ----- Dialogs -----

    if (showReadingGoalChangeDialog) {
        ReadingGoalChangeDialog(
            readingGoal = uiState.readingGoal,
            onDismissRequest = {
                showReadingGoalChangeDialog = false
            },
            onSave = {
                viewModel.updateReadingGoal(it)
            }
        )
    }
}
