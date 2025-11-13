package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.presentation.reading_goal.ReadingGoalViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalContent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components.ReadingGoalTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalScreen(
    navToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadingGoalViewModel = hiltViewModel()
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = {
            ReadingGoalTopAppBar()
        },
        content = { paddingValues ->
            ReadingGoalContent(
                stateUi = stateUi,
                onChangeGoalClick = { /* TODO: implement */ },
                onBookClick = { navToBookDetails(it.id.toString()) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}
