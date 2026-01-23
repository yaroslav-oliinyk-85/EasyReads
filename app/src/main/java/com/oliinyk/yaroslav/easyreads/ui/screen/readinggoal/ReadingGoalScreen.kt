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
import java.time.LocalDate

@Composable
fun ReadingGoalScreen(
    navBack: () -> Unit,
    navToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadingGoalViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentYear by remember { mutableStateOf(LocalDate.now().year) }

    ReadingGoalScreen(
        uiState = uiState,
        navBack = navBack,
        navToBookDetails = navToBookDetails,
        onUpdateReadingGoal = viewModel::updateReadingGoal,
        onPrevClick = {
            if (uiState.oldestYear < (uiState.selectedReadingGoal.year)) {
                viewModel.updateSelectedReading(uiState.selectedReadingGoal.year - 1)
            }
        },
        onNextClick = {
            if (currentYear > (uiState.selectedReadingGoal.year)) {
                viewModel.updateSelectedReading(uiState.selectedReadingGoal.year + 1)
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun ReadingGoalScreen(
    uiState: ReadingGoalUiState,
    navBack: () -> Unit,
    navToBookDetails: (String) -> Unit,
    onUpdateReadingGoal: (ReadingGoal) -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showReadingGoalChangeDialog by rememberSaveable { mutableStateOf(false) }
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ReadingGoalTopAppBar(
                yearGoal = uiState.selectedReadingGoal.year,
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
                onPrevClick = onPrevClick,
                onNextClick = onNextClick,
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
            readingGoal = uiState.selectedReadingGoal,
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
                    finishedBooks =
                        listOf(
                            Book(title = "Book 1"),
                            Book(title = "Book 2"),
                            Book(title = "Book 3"),
                            Book(title = "Book 4"),
                        ),
                    finishedBooksCount = 6,
                    selectedReadingGoal =
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
            onPrevClick = {},
            onNextClick = {},
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
            onPrevClick = {},
            onNextClick = {},
        )
    }
}
