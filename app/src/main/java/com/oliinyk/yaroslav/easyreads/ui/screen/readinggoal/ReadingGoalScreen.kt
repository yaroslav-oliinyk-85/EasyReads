package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components.ReadingGoalChangeDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components.ReadingGoalContent
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components.ReadingGoalTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingGoalScreen(
    navBack: () -> Unit,
    navToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadingGoalViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReadingGoalScreen(
        uiState = uiState,
        navBack = navBack,
        navToBookDetails = navToBookDetails,
        onUpdateReadingGoal = viewModel::updateReadingGoal,
        modifier = modifier,
    )
}

@Composable
internal fun ReadingGoalScreen(
    uiState: ReadingGoalUiState,
    navBack: () -> Unit,
    navToBookDetails: (String) -> Unit,
    onUpdateReadingGoal: (ReadingGoal) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showReadingGoalChangeDialog by rememberSaveable { mutableStateOf(false) }
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ReadingGoalTopAppBar(
                yearGoal = uiState.readingGoal.year,
                navBack = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navBack()
                    }
                },
            )
        },
        content = { paddingValues ->
            ReadingGoalContent(
                uiState = uiState,
                onChangeGoalClick = {
                    showReadingGoalChangeDialog = true
                },
                onBookClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookDetails(it.id.toString())
                    }
                },
                modifier = Modifier.padding(paddingValues),
            )
        },
    )

    // ----- Dialogs -----

    if (showReadingGoalChangeDialog) {
        ReadingGoalChangeDialog(
            readingGoal = uiState.readingGoal,
            onDismissRequest = {
                showReadingGoalChangeDialog = false
            },
            onSave = {
                onUpdateReadingGoal(it)
            },
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun ReadingGoalScreenPreview() {
    EasyReadsTheme {
        ReadingGoalScreen(
            uiState =
                ReadingGoalUiState(
                    books =
                        listOf(
                            Book(title = "Book 1"),
                            Book(title = "Book 2"),
                            Book(title = "Book 3"),
                            Book(title = "Book 4"),
                        ),
                    currentYearFinishedBooksCount = 6,
                    readingGoal =
                        ReadingGoal(
                            year = 2026,
                            goal = 12,
                        ),
                    readPages = 1024,
                    averagePagesHour = 50,
                    readHours = 32,
                    readMinutes = 0,
                ),
            navBack = {},
            navToBookDetails = {},
            onUpdateReadingGoal = {},
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun ReadingGoalScreenEmptyPreview() {
    EasyReadsTheme {
        ReadingGoalScreen(
            uiState = ReadingGoalUiState(),
            navBack = {},
            navToBookDetails = {},
            onUpdateReadingGoal = {},
        )
    }
}
