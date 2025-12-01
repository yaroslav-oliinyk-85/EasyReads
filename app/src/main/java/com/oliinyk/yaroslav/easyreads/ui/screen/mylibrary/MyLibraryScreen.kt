package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryContent
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryTopAppBar

@Composable
fun MyLibraryScreen(
    navToBookAdd: () -> Unit,
    navToReadingGoal: () -> Unit,
    navToBookListByShelvesType: (String) -> Unit,
    navToBookList: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyLibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(onAddBookClick = navToBookAdd)
        },
        content = { paddingValues ->
            MyLibraryContent(
                uiState = uiState,
                onReadingGoalClick = navToReadingGoal,
                onShelfClick = { navToBookListByShelvesType(it.toString()) },
                onSeeAllClick = { navToBookList() },
                modifier = Modifier.padding(paddingValues),
            )
        },
    )
}
