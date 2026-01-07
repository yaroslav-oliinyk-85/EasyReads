package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    navToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyLibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(
                onAddBookClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookAdd()
                    }
                },
                onSettingsClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToSettings()
                    }
                },
            )
        },
        content = { paddingValues ->
            MyLibraryContent(
                uiState = uiState,
                onReadingGoalClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToReadingGoal()
                    }
                },
                onShelfClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookListByShelvesType(it.toString())
                    }
                },
                onSeeAllClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookList()
                    }
                },
                modifier = Modifier.padding(paddingValues),
            )
        },
    )
}
