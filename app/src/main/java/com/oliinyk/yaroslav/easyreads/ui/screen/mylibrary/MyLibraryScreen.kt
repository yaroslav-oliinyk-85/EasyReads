package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.ui.components.AppFloatingActionButton
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryContent
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components.MyLibraryTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components.ReadingGoalChangeDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

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

    MyLibraryScreen(
        uiState = uiState,
        navToBookAdd = navToBookAdd,
        navToReadingGoal = navToReadingGoal,
        navToBookListByShelvesType = navToBookListByShelvesType,
        navToBookList = navToBookList,
        navToSettings = navToSettings,
        onUpdateReadingGoal = viewModel::updateReadingGoal,
        modifier = modifier,
    )
}

@Composable
internal fun MyLibraryScreen(
    uiState: MyLibraryUiState,
    navToBookAdd: () -> Unit,
    navToReadingGoal: () -> Unit,
    navToBookListByShelvesType: (String) -> Unit,
    navToBookList: () -> Unit,
    navToSettings: () -> Unit,
    onUpdateReadingGoal: (ReadingGoal) -> Unit,
    modifier: Modifier = Modifier,
) {
    var openChangeGoalDialog by rememberSaveable { mutableStateOf(false) }
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(
                onSettingsClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToSettings()
                    }
                },
            )
        },
        floatingActionButton = {
            if (uiState.totalBooksCount > 0) {
                AppFloatingActionButton(
                    onClick = {
                        navToBookAdd()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.menu_item__add_text),
                        )
                    },
                )
            }
        },
        content = { paddingValues ->
            MyLibraryContent(
                uiState = uiState,
                onAddBookClick = navToBookAdd,
                onReadingGoalClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToReadingGoal()
                    }
                },
                onChangeGoalClicked = {
                    openChangeGoalDialog = true
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

    // ----- Dialogs -----

    if (openChangeGoalDialog) {
        ReadingGoalChangeDialog(
            readingGoal = uiState.readingGoal,
            onDismissRequest = {
                openChangeGoalDialog = false
            },
            onSave = {
                onUpdateReadingGoal(it)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyLibraryScreenPreview() {
    EasyReadsTheme {
        MyLibraryScreen(
            uiState = MyLibraryUiState(),
            navToBookAdd = { },
            navToReadingGoal = { },
            navToBookListByShelvesType = { },
            navToBookList = { },
            navToSettings = { },
            onUpdateReadingGoal = {},
        )
    }
}
