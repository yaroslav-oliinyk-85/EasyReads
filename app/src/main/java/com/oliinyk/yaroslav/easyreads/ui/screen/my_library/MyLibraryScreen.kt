package com.oliinyk.yaroslav.easyreads.ui.screen.my_library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.presentation.my_library.MyLibraryViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.MyLibraryContent
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components.MyLibraryTopAppBar

@Composable
fun MyLibraryScreen(
    viewModel: MyLibraryViewModel,
    onReadingGoalClick: () -> Unit,
    onShelfClick: (BookShelvesType) -> Unit,
    onSeeAllClick: () -> Unit,
    onAddBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MyLibraryTopAppBar(onAddBookClick = onAddBookClick)
        },
        content = { paddingValues ->
            MyLibraryContent(
                stateUi = stateUi,
                onReadingGoalClick = onReadingGoalClick,
                onShelfClick = onShelfClick,
                onSeeAllClick = onSeeAllClick,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}
