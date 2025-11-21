package com.oliinyk.yaroslav.easyreads.ui.screen.my_library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.MyLibraryContent
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.MyLibraryTopAppBar

@Composable
fun MyLibraryScreen(
    navToBookAdd: () -> Unit,
    navToReadingGoal: () -> Unit,
    navToBookListByShelvesType: (String) -> Unit,
    navToBookList: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyLibraryViewModel = hiltViewModel()
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(onAddBookClick = navToBookAdd)
        },
        content = { paddingValues ->
            MyLibraryContent(
                stateUi = stateUi,
                onReadingGoalClick = navToReadingGoal,
                onShelfClick = { navToBookListByShelvesType(it.toString()) },
                onSeeAllClick = { navToBookList() },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}
